package edu.project.ruangong.form

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Future
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * @author  athonyw
 * @date  2019/11/17 3:53 下午
 * @version init
 */

@Table(name = "activity", schema = "stu_union", catalog = "")
data class ActForm(

        var activityid:String?=null,

//        @NotEmpty(message = "活动名称不可为空")
        @Column(name = "activityname")
        var activityname: String,

        @NotNull(message = "参与人员数量不可为空")
        @Column(name = "peopleamount")
        var peopleamount: Int,

        @Column(name = "departmentassist")
        var departmentassist: String?,

        @Column(name = "starttime")
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Future(message = "请确定一个未来的开始时间")
        var starttime: java.util.Date,

        @Column(name = "overtime")
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Future(message = "请确定一个未来的结束时间")
        var overtime: java.util.Date,

        @Column(name = "applydep")
        @NotEmpty(message = "申请部门不可为空")
        var applydep: String?=null,

        @Column(name = "actlocation")
        @NotEmpty(message = "申请地点不可为空")
        var actlocation: String?=null,

        @Column(name = "actcontent")
        @NotEmpty(message = "申请内容不可为空")
        var actcontent: String?=null,

        @Column(name = "applyuserid")
        @NotEmpty(message = "申请学生id不可为空")
        var applyuserid: String?=null

){
        @Column(name = "judgeid")
//        @NotEmpty(message = "审批id不可为空")
        var judgeid: String?=null
}