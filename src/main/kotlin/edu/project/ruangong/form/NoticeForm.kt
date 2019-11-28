package edu.project.ruangong.form

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

/**
 * @author  athonyw
 * @date  2019/11/27 10:37 上午
 * @version init
 */
@Table(name = "notice", schema = "stu_union", catalog = "")
data class NoticeForm(
        @Id
        @get:Column(name = "informid", nullable = false)
        @NotEmpty(message = "informid不可为空")
        var informid: String? = null,

        @get:Column(name = "informerid", nullable = false, insertable = false, updatable = false)
        @NotEmpty(message = "informerid不可为空")
        var informerid: String? = null,

        @get:Column(name = "informtype", nullable = false)
        @NotEmpty(message = "informtype不可为空")
        var informtype: Int? = null,

        @get:Column(name = "userid", nullable = false, insertable = false, updatable = false)
        @NotEmpty(message = "userid不可为空")
        var userid: String? = null,


        @get:Column(name = "checknum", nullable = false)
        var checknum: Int? = null,

        @Column(name = "depuid")
        @NotEmpty(message = "depuid不可为空")
        var depuid: String?=null
){
    @Column(name = "deptime")
    var starttime: Timestamp= Timestamp(System.currentTimeMillis())

    @Column(name = "canceltime")
    var overtime: Timestamp?=null

    @Column(name = "noticetype")
    var noticetype: String?=null
}