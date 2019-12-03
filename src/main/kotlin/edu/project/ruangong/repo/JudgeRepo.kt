package edu.project.ruangong.repo

import edu.project.ruangong.dao.mapper.JudgeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * @author  athonyw
 * @date  2019/11/17 3:46 下午
 * @version init
 */

interface JudgeRepo:JpaRepository<JudgeEntity,String> {
    fun findJudgeEntitiesByJudgecontentid(id:String):List<JudgeEntity>

//    fun findJudgeEntitiesByJudgecontentid(id:String):JudgeEntity

    fun findJudgeEntitiesByJudgetype(type:Int):List<JudgeEntity>

    @Query(value = "select * from judge where judgetype=? limit ? offset ?",nativeQuery = true)
    fun findJudgeEntitiesByJudgetypeandlimit(type:Int,limit:Int,offset:Int):List<JudgeEntity>

    @Query(value = "select  count(*) from judge", nativeQuery = true)
    fun coutjudgecount(): Int


    @Query(value = "SELECT count(*) FROM judge where DATE_SUB(CURDATE(), INTERVAL ? DAY) = date(handintime)", nativeQuery = true)
    fun coutjudgecountbyinterval(interval: Int): Int
}