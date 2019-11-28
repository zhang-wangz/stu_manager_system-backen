package edu.project.ruangong.service

import edu.project.ruangong.dao.mapper.ActivityEntity
import edu.project.ruangong.dao.mapper.QingjiaEntity
import edu.project.ruangong.dao.mapper.User
import org.springframework.stereotype.Service

/**
 * @author  athonyw
 * @date  2019/11/18 10:21 上午
 * @version init
 */
@Service
interface NoticeService {
    fun noticeAct(act:ActivityEntity)

    fun noticeQing(qingjia:QingjiaEntity)

    fun getuserlistBytype(type:String):List<User>?
}