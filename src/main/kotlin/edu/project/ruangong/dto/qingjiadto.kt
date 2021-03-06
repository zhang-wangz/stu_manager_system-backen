package edu.project.ruangong.dto

import com.fasterxml.jackson.annotation.JsonFormat
import edu.project.ruangong.utils.JudgeUtils
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author  athonyw
 * @date  2019/12/2 2:42 下午
 * @version init
 */
@Table(name = "qingjia")
data class qingjiadto(
        @Id
        @Column(name = "vacateid", nullable = false)
        var vacateid: String? = null,

        @Column(name = "vacateperson", nullable = false)
        var vacateperson: String? = null,

        @Column(name = "persondepartment", nullable = false)
        var persondepartment: String? = null,

        @Column(name = "vacatereason", nullable = false)
        var vacatereason: String? = null
) {
    @Column(name = "leavetime", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    lateinit var leavetime: java.util.Date

    @Column(name = "backtime", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    lateinit var backtime: java.util.Date

    @Column(name = "judgeid", nullable = false)
    var judgeid: String? = null

    @Column(name = "iscancel")
    var iscancel:Int = 0

//    var isjudgestr:String? = JudgeUtils.getbycode(is)
}