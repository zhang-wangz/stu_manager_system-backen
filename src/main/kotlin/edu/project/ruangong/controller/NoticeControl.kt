package edu.project.ruangong.controller

import edu.project.ruangong.dao.mapper.NoticeEntity
import edu.project.ruangong.form.NoticeForm
import edu.project.ruangong.repo.NoticeRepo
import edu.project.ruangong.repo.UserBaseRepository
import edu.project.ruangong.service.NoticeService
import edu.project.ruangong.utils.KeyUtil
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.net.BindException
import java.sql.Timestamp
import javax.validation.Valid

/**
 * @author  athonyw
 * @date  2019/11/27 9:31 上午
 * @version init
 */


@RestController
@RequestMapping("/notice")
class NoticeControl(val userBaseRepository: UserBaseRepository,
                    val noticeRepo: NoticeRepo,
                    val noticeService: NoticeService) {

    @GetMapping("/getnoticeByuid")
    fun getnoticeByuid(@RequestParam(name = "uid")uid:String?): ResultVO<Any>? =
        ResultUtil.success(noticeRepo.findNoticeEntityByUserid(uid))

    @PostMapping("/postnoticeZidingyi")
    fun postnoticeZidingyi(@Valid noticeForm: NoticeForm,
                           bindRes: BindingResult):ResultVO<Any?>{
        if(bindRes.hasErrors()) return ResultUtil.error(-1, bindRes.getFieldError()!!.defaultMessage)
        val userlist = noticeService.getuserlistBytype(noticeForm.noticetype!!)
        userlist!!.forEach {
            NoticeEntity(KeyUtil.getUniqueKey(), noticeForm.informerid, 1, it.uid, 0, noticeForm.depuid).apply {
                this.starttime = Timestamp(System.currentTimeMillis())
                noticeRepo.save(this)
            }
        }
        return ResultUtil.success()
    }


}
