package edu.project.ruangong.controller

import edu.project.ruangong.dao.mapper.JudgeEntity
import edu.project.ruangong.dao.mapper.QingjiaEntity
import edu.project.ruangong.dao.mapper.User
import edu.project.ruangong.form.QingjiaForm
import edu.project.ruangong.repo.*
import edu.project.ruangong.service.impl.JudgeServiceImpl
import edu.project.ruangong.service.impl.NoticeServiceImpl
import edu.project.ruangong.utils.KeyUtil
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import org.springframework.beans.BeanUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.util.*
import javax.validation.Valid
import kotlin.collections.ArrayList

/**
 * @author  athonyw
 * @date  2019/11/26 10:45 下午
 * @version init
 */


@RestController
@RequestMapping("/qingjia")
class QingControl(private val actrepo: ActRepo,
                  private val judrepo: JudgeRepo,
                  private val userBaseRepository: UserBaseRepository,
                  private val departRepo: DepartRepo,
                  private val noticerepo: NoticeRepo,
                  private val qingjiaRepo: qingjiaRepo,
                  private val judgeService: JudgeServiceImpl,
                  private val noticeService: NoticeServiceImpl){

    @PostMapping("/addvacate")
    fun addAct(@Valid qingjiaForm: QingjiaForm,
               bindRes: BindingResult): ResultVO<Any?> {
        if(bindRes.hasErrors()) return ResultUtil.error(-1, bindRes.getFieldError()!!.defaultMessage)
//        qingjiaForm.departmentassist?.apply {
//            this.split(",").forEach{
//                departRepo.findDepByDepartmentname(it)?: return ResultUtil.error(-1,"协同部门不存在")
//            }
//        }
        qingjiaForm.apply {
            if(leavetime.before(Timestamp(System.currentTimeMillis()))) return ResultUtil.error(-1,"开始时间必须是一个未来时间")
            if(leavetime.after(backtime)) return ResultUtil.error(-1,"开始时间必须比结束时间早")
        }

        val qingjia = QingjiaEntity("","","","")
        qingjiaForm.vacateid = KeyUtil.getUniqueKey()
        val judgeId:String = KeyUtil.getUniqueKey()
        qingjiaForm.judgeid = judgeId
        BeanUtils.copyProperties(qingjiaForm,qingjia).apply {
            qingjiaRepo.save(qingjia)
        }

        //2为请假表类型
        JudgeEntity(judgeId,2,qingjia.vacateid!!).let {
            it.judgeid = judgeId
            judrepo.save(it)
        }
        return ResultUtil.success(mapOf(
                "actId" to qingjia.vacateid,
                "applyuserId" to qingjia.vacateperson,
                "judgeId" to judgeId
        ))
    }


    @GetMapping("/cancelqingjia/{id}")
    fun cancel(@PathVariable(name = "id") vacateid:String): ResultVO<Any?> {
        val qingjia = qingjiaRepo.findById(vacateid).orElse(null)?.apply {
            if(this.iscancel==1)  return ResultUtil.error(-2,"所查找的请假已删除")
            iscancel = 1
            qingjiaRepo.save(this)
        }?:return ResultUtil.error(-1,"找不到所要查找请假")
        judrepo.findJudgeEntitiesByJudgecontentid(qingjia.vacateid!!).forEach {
            judrepo.delete(it)
        }
        return ResultUtil.success(0,"成功撤销")
    }


    @PostMapping("/editQing")
    fun editAct(@Valid qingjiaForm: QingjiaForm,
                bindRes: BindingResult): ResultVO<Any?> {
        if(bindRes.hasErrors()) return ResultUtil.error(-1, bindRes.getFieldError()!!.defaultMessage)
        qingjiaForm.apply {
            vacateid?:return ResultUtil.error(-1, "id不可为空")
            judgeid?:return ResultUtil.error(-1, "审批id不可为空")
        }
        val qingjia1 = actrepo.findById(qingjiaForm.vacateid!!).orElse(null)?:return ResultUtil.error(-1, "id不存在")
        val qingjia = QingjiaEntity("","","","")
        BeanUtils.copyProperties(qingjiaForm,qingjia).apply {
            qingjia.iscancel = qingjia1.iscancel
            qingjiaRepo.save(qingjia)
        }
        return ResultUtil.success()
    }

    @GetMapping("/judgeQing")
    fun judgeact(@RequestParam(name = "isjudge") isjudge:Int?,
                 @RequestParam(name = "judgeid") judgeid:String?,
                 @RequestParam(name = "vacateid") vacateid:String?): ResultVO<Any?> {
        vacateid?:return ResultUtil.error(-1, "id不可为空")
        isjudge?:return ResultUtil.error(-1, "isjudge不可为空")
        judgeid?:return ResultUtil.error(-1, "judgeid不可为空")
        try {
            judgeService.judge(judgeid,isjudge)
            if(isjudge==1){
                val qingjia = qingjiaRepo.findByIdOrNull(vacateid)?:return ResultUtil.error(-1, "活动id不存在")
                noticeService.noticeQing(qingjia)
            }
        }catch (e:Exception){
            return ResultUtil.error(-2,e.message)
        }
        return ResultUtil.success()
    }

    @GetMapping("/reApplyQingJudge")
    fun reJudge(@RequestParam(name = "vacateid") vacateid:String?): ResultVO<Any?> {
        vacateid?:return ResultUtil.error(-1, "id不可为空")
        JudgeEntity(KeyUtil.getUniqueKey(),1,vacateid).let {
            val qingjia = qingjiaRepo.findByIdOrNull(vacateid)?:return ResultUtil.error(-1, "id不存在")
            qingjia.judgeid = it.judgeid
            qingjiaRepo.save(qingjia)
            judrepo.save(it)
            return ResultUtil.success(mapOf(
                    "judgeId" to it.judgeid
            ))
        }
    }

    @GetMapping("/getQingList")
    fun getQingList(): ResultVO<Any?> =  ResultUtil.success(qingjiaRepo.findAll())

    @GetMapping("/getQingJudgeList")
    fun getQingJudgeList(): ResultVO<Any?> = ResultUtil.success(judrepo.findJudgeEntitiesByJudgetype(2))

    @GetMapping("/getQingByjudgeId")
    fun getactByjudgeId(@RequestParam(name = "judgeId")judgeId:String?): ResultVO<Any?> {
        judgeId?:return ResultUtil.error(-1, "judgeid不可为空")
        val judge = judrepo.findById(judgeId).orElse(null)?.apply {
            if(this.judgetype!=2) ResultUtil.error(-1, "judge不是请假类型")
        }?:return ResultUtil.error(-1, "judge不可为空")
        return ResultUtil.success(actrepo.findById(judge.judgecontentid).orElse(null))
    }

    @GetMapping("getdepMenberQingByministerId")
    //返回的请假表里面是全部的，并没有进行筛选是否已经批准
    fun getdepMenberByfenguan(@RequestParam(value = "ministerId") ministerId: String?): ResultVO<*>? {
        ministerId?:return ResultUtil.error(-1, "ministerId不可为空")
        val minist: User = userBaseRepository.findById(ministerId).orElse(null)
        val userQinglist = mutableListOf<QingjiaEntity>().let { userQinglist->
            userBaseRepository.findUsersByDepartmentsAndSoftdelete(minist.departments,0).forEach{
                qingjiaRepo.findQingjiaEntitysByVacateperson(it.uid)?.apply{
                    userQinglist.addAll(this)
                }
            }
            userQinglist
        }
        return ResultUtil.success(userQinglist)
    }

    @GetMapping("getministMenberQingByfenguanchairId")
    //返回的请假表里面是全部的，并没有进行筛选是否已经批准
    fun getdepMenberQingBychairId(@RequestParam(value = "chairId") chairId: String?): ResultVO<*>? {
        chairId?:return ResultUtil.error(-1, "chairId不可为空")
        val userqing = mutableListOf<QingjiaEntity>()
        departRepo.findDepartmentsByChairmanid(chairId).forEach{
            userBaseRepository.findById(it.ministerid).orElse(null)?.apply {
                qingjiaRepo.findQingjiaEntitysByVacateperson(this.uid)?.apply {
                    userqing.addAll(this)
                }
            }
        }
        return ResultUtil.success(userqing)
    }

    @GetMapping("getfuchairMenberQingByzhengchairId")
    //返回的请假表里面是全部的，并没有进行筛选是否已经批准
    fun getfuchairMenberQingByzhengchairId(@RequestParam(value = "chairId") chairId: String?): ResultVO<*>? {
        chairId?:return ResultUtil.error(-1, "chairId不可为空")
        val userqing = mutableListOf<QingjiaEntity>().let{ userqing->
            userBaseRepository.findUsersByRank("2").forEach(){
                qingjiaRepo.findQingjiaEntitysByVacateperson(it.uid)?.apply {
                    userqing.addAll(this)
                }
            }
            userqing
        }
        return ResultUtil.success(userqing)
    }



}