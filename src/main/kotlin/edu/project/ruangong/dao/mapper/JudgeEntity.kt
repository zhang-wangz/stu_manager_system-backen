package edu.project.ruangong.dao.mapper

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import edu.project.ruangong.enums.applyenum
import edu.project.ruangong.enums.isjudgeEnum
import edu.project.ruangong.utils.EnumUtils
import edu.project.ruangong.utils.JudgeUtils
import java.util.Date
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
    var isjudge: Int? = null

    @Column(name = "handintime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var handintime: Date = Date(System.currentTimeMillis())
}

