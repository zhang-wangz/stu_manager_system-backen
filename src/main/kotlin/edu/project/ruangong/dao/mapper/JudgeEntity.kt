package edu.project.ruangong.dao.mapper

import com.fasterxml.jackson.annotation.JsonIgnore
import edu.project.ruangong.enums.applyenum
import edu.project.ruangong.enums.isjudgeEnum
import edu.project.ruangong.utils.EnumUtils
import edu.project.ruangong.utils.JudgeUtils
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "judge", schema = "stu_union", catalog = "")
data class JudgeEntity(
    @Id
    @Column(name = "judgeid")
    var judgeid: String,

    @Column(name = "judgetype")
    var judgetype: Int? = null,

    @Column(name = "judgecontentid")
    var judgecontentid: String
){
    @Column(name = "isjudge", nullable = true)
    @JsonIgnore
    var isjudge: Int? = null

    var judgetypeStr:String = applyenum.ACTIVITY.msg

    @Column(name = "handintime")
    var handintime: Date = Date(System.currentTimeMillis())

    var isjudgestr:String? = JudgeUtils.getbycode(isjudge)

}

