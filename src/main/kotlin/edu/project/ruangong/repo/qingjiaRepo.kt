package edu.project.ruangong.repo

import edu.project.ruangong.dao.mapper.QingjiaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author  athonyw
 * @date  2019/11/26 10:38 下午
 * @version init
 */
interface qingjiaRepo:JpaRepository<QingjiaEntity,String> {
    fun findQingjiaEntityByVacateperson(uid:String?):QingjiaEntity

    fun findQingjiaEntitysByVacateperson(uid:String?):List<QingjiaEntity>?


}