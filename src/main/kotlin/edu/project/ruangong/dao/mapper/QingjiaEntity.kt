package edu.project.ruangong.dao.mapper

import javax.persistence.*

@Entity
@Table(name = "qingjia")
data class QingjiaEntity(
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
        lateinit var leavetime: java.sql.Timestamp

        @Column(name = "backtime", nullable = false)
        lateinit var backtime: java.sql.Timestamp

        @Column(name = "judgeid", nullable = false)
        var judgeid: String? = null

        @Column(name = "iscancel")
        var iscancel:Int = 0
}

