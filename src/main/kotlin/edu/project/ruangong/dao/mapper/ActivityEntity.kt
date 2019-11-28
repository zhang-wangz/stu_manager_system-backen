package edu.project.ruangong.dao.mapper

import java.sql.Date
import java.sql.Timestamp
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
        lateinit var starttime: Timestamp

        @Column(name = "overtime")
        lateinit var overtime: Timestamp

        @Column(name = "iscancel")
        var iscancel:Int = 0
}

