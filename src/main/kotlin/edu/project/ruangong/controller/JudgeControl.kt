package edu.project.ruangong.controller

import edu.project.ruangong.form.Judgedto
import edu.project.ruangong.repo.ActRepo
import edu.project.ruangong.repo.JudgeRepo
import edu.project.ruangong.repo.qingjiaRepo
import edu.project.ruangong.utils.JudgeUtils
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import org.springframework.beans.BeanUtils
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  athonyw
 * @date  2019/11/26 10:12 下午
 * @version init
 */

@RestController
@RequestMapping("/judge")
class JudgeControl(private val judgeRepo: JudgeRepo,
                   private val qingjiaRepo: qingjiaRepo,
                   private val actRepo: ActRepo){

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone= TimeZone.getTimeZone("GMT+:08:00")
        dateFormat.isLenient = false
        binder.registerCustomEditor(Date::class.java, CustomDateEditor(dateFormat, true))
    }

    @GetMapping("/getapplyInfoById")
    fun getapplyInfoById(@RequestParam(name = "applyId")applyId:String?):ResultVO<Any?>{
        applyId?:return ResultUtil.error(-1,"申请者学号不可为空")

        val userqing = qingjiaRepo.findQingjiaEntitysByVacateperson(applyId)
        val userqingJudge = mutableListOf<Judgedto>().let { userqingJudge->
            userqing?.forEach {
                judgeRepo.findJudgeEntitiesByJudgecontentid(it.vacateid!!).forEach {
                    val judgedto = Judgedto("", 0, "")
                    BeanUtils.copyProperties(it, judgedto)
                    judgedto.isjudgestr = JudgeUtils.getbycode(judgedto.isjudge)!!
                    judgedto.judgetypeStr = JudgeUtils.gettypebycode(judgedto.judgetype)!!
                    userqingJudge.add(judgedto)
                }
            }
            userqingJudge
        }

        val useract = actRepo.findActivityEntitiesByApplyuserid(applyId)
        val useractJudge = mutableListOf<Judgedto>().let { useractJudge->
            useract.forEach {
                judgeRepo.findJudgeEntitiesByJudgecontentid(it.activityid).forEach {
                    val judgedto = Judgedto("", 0, "")
                    BeanUtils.copyProperties(it, judgedto)
                    judgedto.isjudgestr = JudgeUtils.getbycode(judgedto.isjudge)!!
                    judgedto.judgetypeStr = JudgeUtils.gettypebycode(judgedto.judgetype)!!
                    useractJudge.add(judgedto)
                }
            }
            useractJudge
        }

        return ResultUtil.success(mapOf(
            "act" to useractJudge,
            "qing" to userqingJudge
        ))
    }
}