package edu.project.ruangong.repo

import edu.project.ruangong.dao.mapper.NoticeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * @author  athonyw
 * @date  2019/11/17 8:41 下午
 * @version init
 */
interface NoticeRepo:JpaRepository<NoticeEntity,String> {
    fun findNoticeEntityByUserid(uid:String?):NoticeEntity

    fun findNoticeEntitiesByUserid(uid:String?):List<NoticeEntity>

    fun findNoticeEntitiesByInformerid(uid:String?):List<NoticeEntity>

    @Query(value = "select  count(*) from notice", nativeQuery = true)
    fun coutnoticecount(): Int

    @Query(value = "SELECT count(*) FROM notice where DATE_SUB(CURDATE(), INTERVAL ? DAY) = date(deptime)", nativeQuery = true)
    fun coutnoticecountbyinterval(interval: Int): Int
}