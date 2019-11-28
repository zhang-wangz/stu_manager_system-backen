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

//    @Query("",nativeQuery = true)
    fun findJudgeEntitiesByJudgetype(type:Int?):List<JudgeEntity>

//    fun findJudgeEntitiesBy
}