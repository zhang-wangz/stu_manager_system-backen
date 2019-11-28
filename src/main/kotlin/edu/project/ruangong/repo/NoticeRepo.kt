package edu.project.ruangong.repo

import edu.project.ruangong.dao.mapper.NoticeEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author  athonyw
 * @date  2019/11/17 8:41 下午
 * @version init
 */
interface NoticeRepo:JpaRepository<NoticeEntity,String> {
    fun findNoticeEntityByUserid(uid:String?):NoticeEntity

    fun findNoticeEntitiesByInformerid(uid:String?):List<NoticeEntity>
}