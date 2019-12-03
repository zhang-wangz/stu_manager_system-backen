package edu.project.ruangong.form

import com.fasterxml.jackson.annotation.JsonIgnore
import edu.project.ruangong.enums.applyenum
import edu.project.ruangong.utils.JudgeUtils
import java.sql.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author  athonyw
 * @date  2019/11/30 6:51 下午
 * @version init
 */

data class Judgedto(
        @Column(name = "judgeid")
        var judgeid: String,

        @Column(name = "judgetype")
        @JsonIgnore
        var judgetype: Int? = null,

        @Column(name = "judgecontentid")
        var judgecontentid: String
){
    @Column(name = "isjudge", nullable = true)
    @JsonIgnore
    var isjudge: Int? = null

    var judgetypeStr:String? = JudgeUtils.gettypebycode(judgetype)

    @Column(name = "handintime")
    var handintime: Date = Date(System.currentTimeMillis())

    var isjudgestr:String? = JudgeUtils.getbycode(isjudge)
}