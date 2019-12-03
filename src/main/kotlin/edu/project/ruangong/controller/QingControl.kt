package edu.project.ruangong.controller

import edu.project.ruangong.dao.mapper.JudgeEntity
import edu.project.ruangong.dao.mapper.QingjiaEntity
import edu.project.ruangong.dao.mapper.User
import edu.project.ruangong.dto.qingjiadto
import edu.project.ruangong.form.Judgedto
import edu.project.ruangong.form.QingjiaForm
import edu.project.ruangong.repo.*
import edu.project.ruangong.service.impl.JudgeServiceImpl
import edu.project.ruangong.service.impl.NoticeServiceImpl
import edu.project.ruangong.utils.JudgeUtils
import edu.project.ruangong.utils.KeyUtil
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import io.swagger.annotations.*
import lombok.extern.log4j.Log4j2
import org.springframework.beans.BeanUtils
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Valid
import kotlin.collections.ArrayList

/**
 * @author  athonyw
 * @date  2019/11/26 10:45 下午
 * @version init
 */
@Api(value = "qingjia接口")
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

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone= TimeZone.getTimeZone("GMT+:08:00")
        dateFormat.isLenient = false
        binder.registerCustomEditor(Date::class.java, CustomDateEditor(dateFormat, true))
    }

    @PostMapping("/addvacate")
    @ApiResponse(code = 0, message = "操作成功")
    fun addAct(@ApiParam(name="传入对象",value="传入json格式",required=true)@Valid qingjiaForm: QingjiaForm,
               bindRes: BindingResult): ResultVO<Any?> {
        if(bindRes.hasErrors()) return ResultUtil.error(-1, bindRes.getFieldError()!!.defaultMessage)
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
                "vacateid" to qingjia.vacateid,
                "applyuserId" to qingjia.vacateperson,
                "judgeId" to judgeId
        ))
    }


    @GetMapping("/cancelqingjia/{id}")
    @ApiOperation(value = "根据uid取消请假", notes = "根据uid取消请假")
    @ApiImplicitParam(name = "id",value = "vacateid",required = true)
    fun cancel(@PathVariable(name = "id") vacateid:String?): ResultVO<Any> {
        vacateid?:return ResultUtil.error(-1, "id不可为空")
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
    @ApiOperation(value = "根据qingjiaForm edit请假", notes = "根据qingjiaForm edit请假")
    fun editAct(@ApiParam(name="传入对象",value="传入json格式",required=true)@Valid qingjiaForm: QingjiaForm,
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
    @ApiOperation(value = "根据judgeid等审批请假", notes = "根据judgeid等审批请假")

    fun judgeact(@ApiParam("isjudge") @RequestParam(name = "isjudge") isjudge:Int?,
                 @ApiParam("judgeid") @RequestParam(name = "judgeid") judgeid:String?,
                 @ApiParam("vacateid") @RequestParam(name = "vacateid") vacateid:String?): ResultVO<Any?> {
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
        }?:return ResultUtil.error(-1, "judge")
        return ResultUtil.success(qingjiaRepo.findById(judge.judgecontentid).orElse(null))
    }

    @GetMapping("getdepMenberQingByministerId")
    //返回的请假表里面是全部的，并没有进行筛选是否已经批准
    fun getdepMenberByfenguan(@RequestParam(name = "ministerId") ministerId: String?): ResultVO<*>? {
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
        val userqingJudge = mutableListOf<Judgedto>().let { userqingJudge->
            userQinglist.forEach {
                judrepo.findJudgeEntitiesByJudgecontentid(it.vacateid!!).forEach {
                    val judgedto=Judgedto("",0,"")
                    BeanUtils.copyProperties(it,judgedto)
                    judgedto.isjudgestr = JudgeUtils.getbycode(judgedto.isjudge)!!
                    judgedto.judgetypeStr = JudgeUtils.gettypebycode(judgedto.judgetype)!!
                    userqingJudge.add(judgedto)
                }
            }
            userqingJudge
        }
        return ResultUtil.success(userqingJudge)
    }

    @GetMapping("getministMenberQingByfenguanchairId")
    //返回的请假表里面是全部的，并没有进行筛选是否已经批准
    fun getdepMenberQingBychairId(@RequestParam(name = "chairId") chairId: String?): ResultVO<*>? {
        chairId?:return ResultUtil.error(-1, "chairId不可为空")
        val userqing = mutableListOf<QingjiaEntity>()
        departRepo.findDepartmentsByChairmanid(chairId).forEach{
            userBaseRepository.findById(it.ministerid).orElse(null)?.apply {
                qingjiaRepo.findQingjiaEntitysByVacateperson(this.uid)?.apply {
                    userqing.addAll(this)
                }
            }
        }

        val userqingJudge = mutableListOf<Judgedto>().let { userqingJudge->
            userqing.forEach {
                judrepo.findJudgeEntitiesByJudgecontentid(it.vacateid!!).forEach {
                    val judgedto=Judgedto("",0,"")
                    BeanUtils.copyProperties(it,judgedto)
                    judgedto.isjudgestr = JudgeUtils.getbycode(judgedto.isjudge)!!
                    judgedto.judgetypeStr = JudgeUtils.gettypebycode(judgedto.judgetype)!!
                    userqingJudge.add(judgedto)
                }
            }
            userqingJudge
        }
        return ResultUtil.success(userqingJudge)
    }

    @GetMapping("getfuchairMenberQingByzhengchairId")
    //返回的请假表里面是全部的，并没有进行筛选是否已经批准
    fun getfuchairMenberQingByzhengchairId(@RequestParam(name = "chairId") chairId: String?): ResultVO<*>? {
        chairId?:return ResultUtil.error(-1, "chairId不可为空")
        val userqing = mutableListOf<QingjiaEntity>().let{ userqing->
            userBaseRepository.findUsersByRank("2").forEach(){
                qingjiaRepo.findQingjiaEntitysByVacateperson(it.uid)?.apply {
                    userqing.addAll(this)
                }
            }
            userqing
        }
        val userqingJudge = mutableListOf<Judgedto>().let { userqingJudge->
            userqing.forEach {
                judrepo.findJudgeEntitiesByJudgecontentid(it.vacateid!!).forEach {
                    val judgedto=Judgedto("",0,"")
                    BeanUtils.copyProperties(it,judgedto)
                    judgedto.isjudgestr = JudgeUtils.getbycode(judgedto.isjudge)!!
                    judgedto.judgetypeStr = JudgeUtils.gettypebycode(judgedto.judgetype)!!
                    userqingJudge.add(judgedto)
                }
            }
            userqingJudge
        }
        return ResultUtil.success(userqingJudge)
    }

}