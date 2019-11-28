package edu.project.ruangong.controller

import edu.project.ruangong.dao.mapper.ActivityEntity
import edu.project.ruangong.dao.mapper.JudgeEntity
import edu.project.ruangong.dao.mapper.NoticeEntity
import edu.project.ruangong.form.ActForm
import edu.project.ruangong.repo.ActRepo
import edu.project.ruangong.repo.DepartRepo
import edu.project.ruangong.repo.JudgeRepo
import edu.project.ruangong.repo.NoticeRepo
import edu.project.ruangong.service.JudgeService
import edu.project.ruangong.service.NoticeService
import edu.project.ruangong.service.impl.JudgeServiceImpl
import edu.project.ruangong.service.impl.NoticeServiceImpl
import edu.project.ruangong.utils.KeyUtil
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import org.springframework.beans.BeanUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.sql.Date
import java.sql.Timestamp
import javax.validation.Valid

/**
 * @author  athonyw
 * @date  2019/11/17 2:57 下午
 * @version init
 */


@RestController
@RequestMapping("/activity")
class ActControl(private val actrepo:ActRepo,
                 private val judrepo:JudgeRepo,
                 private val departRepo:DepartRepo,
                 private val noticerepo: NoticeRepo,
                 private val judgeService: JudgeServiceImpl,
                 private val noticeService: NoticeServiceImpl){

    @PostMapping("/addAct")
    fun addAct(@Valid actForm:ActForm,
               bindRes: BindingResult):ResultVO<Any?>{
        if(bindRes.hasErrors()) return ResultUtil.error(-1, bindRes.getFieldError()!!.defaultMessage)
        actForm.departmentassist?.apply {
            this.split(",").forEach{
                departRepo.findDepByDepartmentname(it)?: return ResultUtil.error(-1,"协同部门不存在")
            }
        }
        actForm.apply {
            if(starttime.before(Timestamp(System.currentTimeMillis()))) return ResultUtil.error(-1,"开始时间必须是一个未来时间")
            if(starttime.after(overtime)) return ResultUtil.error(-1,"开始时间必须比结束时间早")
        }

        val act = ActivityEntity("","",0,"")
        actForm.activityid = KeyUtil.getUniqueKey()
        val judgeId:String = KeyUtil.getUniqueKey()
        actForm.judgeid = judgeId
        BeanUtils.copyProperties(actForm,act).apply {
            actrepo.save(act)
        }

        //1为活动表类型
        JudgeEntity(judgeId,1,act.activityid).let {
            it.judgeid = judgeId
            judrepo.save(it)
        }
        return ResultUtil.success(mapOf(
                "actId" to act.activityid,
                "applyuserId" to act.applyuserid,
                "judgeId" to judgeId
        ))
    }


    @GetMapping("/cancelAct/{id}")
    fun cancel(@PathVariable(name = "id") actId:String):ResultVO<Any?>{
        val act = actrepo.findById(actId).orElse(null)?.apply {
            if(this.iscancel==1)  return ResultUtil.error(-2,"所查找的活动已删除")
            iscancel = 1
            actrepo.save(this)
        }?:return ResultUtil.error(-1,"找不到所要查找的活动")
        judrepo.findJudgeEntitiesByJudgecontentid(act.activityid).forEach {
            judrepo.delete(it)
        }
        return ResultUtil.success(0,"成功撤销")
    }


    @PostMapping("/editAct")
    fun editAct(@Valid actForm:ActForm,
                bindRes: BindingResult):ResultVO<Any?>{
        if(bindRes.hasErrors()) return ResultUtil.error(-1, bindRes.getFieldError()!!.defaultMessage)
        actForm.apply {
            activityid?:return ResultUtil.error(-1, "活动id不可为空")
            judgeid?:return ResultUtil.error(-1, "审批id不可为空")
        }
        val act1 = actrepo.findById(actForm.activityid!!).orElse(null)?:return ResultUtil.error(-1, "活动id不存在")
        val act = ActivityEntity("","",0,"")
        BeanUtils.copyProperties(actForm,act).apply {
            act.iscancel = act1.iscancel
            actrepo.save(act)
        }
        return ResultUtil.success()
    }

    @GetMapping("/judgeAct")
    fun judgeact(@RequestParam(name = "isjudge") isjudge:Int?,
                 @RequestParam(name = "judgeid") judgeid:String?,
                 @RequestParam(name = "actid") actid:String?):ResultVO<Any?>{
        actid?:return ResultUtil.error(-1, "活动id不可为空")
        isjudge?:return ResultUtil.error(-1, "isjudge不可为空")
        judgeid?:return ResultUtil.error(-1, "judgeid不可为空")
        try {
            judgeService.judge(judgeid,isjudge)
            if(isjudge==1){
                val act = actrepo.findByIdOrNull(actid)?:return ResultUtil.error(-1, "活动id不存在")
                noticeService.noticeAct(act)
            }
        }catch (e:Exception){
            return ResultUtil.error(-2,e.message)
        }
        return ResultUtil.success()
    }

    @GetMapping("/reApplyActJudge")
    fun reJudge(@RequestParam(name = "actid") actid:String?):ResultVO<Any?>{
        actid?:return ResultUtil.error(-1, "活动id不可为空")
        JudgeEntity(KeyUtil.getUniqueKey(),1,actid).let {
            val act = actrepo.findByIdOrNull(actid)?:return ResultUtil.error(-1, "活动id不存在")
            act.judgeid = it.judgeid
            actrepo.save(act)
            judrepo.save(it)
            return ResultUtil.success(mapOf(
                    "judgeId" to it.judgeid
            ))
        }
    }

    @GetMapping("/getActList")
    fun getactList():ResultVO<Any?> =  ResultUtil.success(actrepo.findAll())

    @GetMapping("/getActJudgeList")
    fun getactJudgeList():ResultVO<Any?> = ResultUtil.success(judrepo.findJudgeEntitiesByJudgetype(1))

    @GetMapping("/getactByjudgeId")
    fun getactByjudgeId(@RequestParam(name = "judgeId")judgeId:String?):ResultVO<Any?>{
        judgeId?:return ResultUtil.error(-1, "judgeid不可为空")
        val judge = judrepo.findById(judgeId).orElse(null)?.apply {
            if(this.judgetype!=1) ResultUtil.error(-1, "judge不是活动类型")
        }?:return ResultUtil.error(-1, "judge不可为空")
        return ResultUtil.success(actrepo.findById(judge.judgecontentid).orElse(null))
    }







}