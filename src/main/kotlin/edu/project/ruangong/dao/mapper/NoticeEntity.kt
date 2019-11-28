package edu.project.ruangong.dao.mapper

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author  athonyw
 * @date  2019/11/17 8:36 下午
 * @version init
 */
@Entity
@Table(name = "notice", schema = "stu_union", catalog = "")
data class NoticeEntity(
        @Id
        @get:Column(name = "informid", nullable = false)
        var informid: String? = null,

        @get:Column(name = "informerid", nullable = false, insertable = false, updatable = false)
        var informerid: String? = null,

        @get:Column(name = "informtype", nullable = false)
        var informtype: Int? = null,

        @get:Column(name = "userid", nullable = false, insertable = false, updatable = false)
        var userid: String? = null,


        @get:Column(name = "checknum", nullable = false)
        var checknum: Int? = null,

        @Column(name = "depuid")
        var depuid: String?=null
){
    @Column(name = "deptime")
    lateinit var starttime: Timestamp

    @Column(name = "canceltime")
    var overtime: Timestamp?=null

    @Column(name = "noticetype")
    var noticetype: String?=null
}