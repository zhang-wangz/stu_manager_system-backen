package edu.project.ruangong.repo

import edu.project.ruangong.dao.mapper.ActivityEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author  athonyw
 * @date  2019/11/17 3:46 下午
 * @version init
 */
interface ActRepo :JpaRepository<ActivityEntity,String>{
    fun findActivityEntitiesByApplyuserid(userid:String):List<ActivityEntity>

}