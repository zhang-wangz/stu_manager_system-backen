package edu.project.ruangong.controller

import edu.project.ruangong.repo.ActRepo
import edu.project.ruangong.repo.JudgeRepo
import edu.project.ruangong.repo.qingjiaRepo
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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

    @GetMapping("/getapplyInfoById")
    fun getapplyInfoById(@RequestParam(value = "applyId")applyId:String?):ResultVO<Any?>{
        applyId?:return ResultUtil.error(-1,"申请者学号不可为空")
        val list = ArrayList<Any>().apply {
            this.add(actRepo.findActivityEntitiesByApplyuserid(applyId))
            this.add(qingjiaRepo.findQingjiaEntityByVacateperson(applyId)!!)
        }
        return ResultUtil.success(list)
    }
}