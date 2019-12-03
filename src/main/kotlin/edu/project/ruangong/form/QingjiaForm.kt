package edu.project.ruangong.form

import org.springframework.format.annotation.DateTimeFormat
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Future
import javax.validation.constraints.NotEmpty

/**
 * @author  athonyw
 * @date  2019/11/26 10:39 下午
 * @version init
 */
@Table(name = "qingjia")
data class QingjiaForm(

        var vacateid: String? = null,

        @Column(name = "vacateperson", nullable = false)
        @NotEmpty(message = "请假人不可为空")
        var vacateperson: String? = null,

        @Column(name = "persondepartment", nullable = false)
        @NotEmpty(message = "请假人所属部门不可为空")
        var persondepartment: String? = null,

        @Column(name = "vacatereason", nullable = false)
        @NotEmpty(message = "请假理由不可为空")
        var vacatereason: String? = null,

        @Column(name = "leavetime", nullable = false)
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Future(message = "请确定一个未来的结束时间")
        var leavetime: java.util.Date,

        @Column(name = "backtime", nullable = false)
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Future(message = "请确定一个未来的结束时间")
        var backtime: java.util.Date
) {

    @Column(name = "judgeid")
    var judgeid: String? = null
}