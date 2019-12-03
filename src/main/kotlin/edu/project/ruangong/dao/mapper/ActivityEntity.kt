package edu.project.ruangong.dao.mapper

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "activity", schema = "stu_union", catalog = "")
data class ActivityEntity(
        @Id
        @Column(name = "activityid")
        var activityid: String,

        @Column(name = "activityname")
        var activityname: String,

        @Column(name = "peopleamount")
        var peopleamount: Int,

        @Column(name = "departmentassist")
        var departmentassist: String?=null
){
        @Column(name = "applydep")
        var applydep: String?=null

        @Column(name = "actlocation")
        var actlocation: String?=null


        @Column(name = "actcontent")
        var actcontent: String?=null

        @Column(name = "applyuserid")
        var applyuserid: String?=null

        @Column(name = "judgeid")
        lateinit var judgeid: String

        @Column(name = "starttime")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        lateinit var starttime: Date

        @Column(name = "overtime")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        lateinit var overtime: Date

        @Column(name = "iscancel")
        var iscancel:Int = 0
}

