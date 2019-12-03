package edu.project.ruangong.controller

import edu.project.ruangong.dao.mapper.NoticeEntity
import edu.project.ruangong.form.NoticeForm
import edu.project.ruangong.repo.NoticeRepo
import edu.project.ruangong.repo.UserBaseRepository
import edu.project.ruangong.service.NoticeService
import edu.project.ruangong.utils.KeyUtil
import edu.project.ruangong.utils.ResultUtil
import edu.project.ruangong.vo.ResultVO
import io.swagger.annotations.*
import lombok.extern.log4j.Log4j2
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.net.BindException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Valid

/**
 * @author  athonyw
 * @date  2019/11/27 9:31 上午
 * @version init
 */

@Api(value = "notice接口")
@Log4j2
@RestController
@RequestMapping("/notice")
class NoticeControl(val userBaseRepository: UserBaseRepository,
                    val noticeRepo: NoticeRepo,
                    val noticeService: NoticeService) {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone= TimeZone.getTimeZone("GMT+:08:00")
        dateFormat.isLenient = false
        binder.registerCustomEditor(Date::class.java, CustomDateEditor(dateFormat, true))
    }

    @GetMapping("/getnoticeByuid")
    @ApiOperation(value = "获取通知内容", notes = "根据uid查询通知内容")
    @ApiImplicitParam(name = "uid", value = "用户id", required=true, dataType = "String")
    @ApiResponse(code = 0, message = "操作成功")
    fun getnoticeByuid(@RequestParam(name = "uid")uid:String?): ResultVO<Any>? =
        ResultUtil.success(noticeRepo.findNoticeEntityByUserid(uid))

    @PostMapping("/postnoticeZidingyi")
    @ApiOperation(value = "提交通知内容", notes = "根据NoticeForm提交通知内容")
    fun postnoticeZidingyi(@ApiParam(name="传入对象",value="传入json格式",required=true)@Valid noticeForm: NoticeForm,
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
