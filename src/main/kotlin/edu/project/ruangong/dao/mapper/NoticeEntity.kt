package edu.project.ruangong.dao.mapper

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    lateinit var starttime: Date

    @Column(name = "canceltime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var overtime: Date?=null

    @Column(name = "noticetype")
    var noticetype: String?=null
}