package edu.project.ruangong.service.impl

import edu.project.ruangong.dao.mapper.ActivityEntity
import edu.project.ruangong.dao.mapper.NoticeEntity
import edu.project.ruangong.dao.mapper.QingjiaEntity
import edu.project.ruangong.dao.mapper.User
import edu.project.ruangong.repo.DepartRepo
import edu.project.ruangong.repo.NoticeRepo
import edu.project.ruangong.repo.UserBaseRepository
import edu.project.ruangong.service.NoticeService
import edu.project.ruangong.utils.KeyUtil
import org.springframework.stereotype.Service
import java.sql.Timestamp

/**
 * @author  athonyw
 * @date  2019/11/18 10:22 上午
 * @version init
 */
@Service
class NoticeServiceImpl(private val userRepo:UserBaseRepository,
                        private val noticeRepo:NoticeRepo,
                        private val departRepo: DepartRepo) :NoticeService{

    override fun noticeAct(act:ActivityEntity) {
        val userlist:List<User> = act.departmentassist?.split(",").let { deplist->
            mutableListOf<User>().let { userlist->
                 deplist?.forEach{
                     departRepo.findDepByDepartmentname(it)?:throw RuntimeException("协同部门不存在")
                     userlist.addAll(userRepo.findUsersByDepartments(it))
                 }
                userlist.forEach(::println)

                act.applydep?.apply {
                    departRepo.findDepByDepartmentname(this)?:throw RuntimeException("主办部门不存在")
                    userlist.addAll(userRepo.findUsersByDepartments(this))
                }
                userlist
            }
        }
        userlist.forEach{
            NoticeEntity(KeyUtil.getUniqueKey(),act.activityid,1,it.uid,0,"系统通知").apply {
                this.starttime = Timestamp(System.currentTimeMillis())
                noticeRepo.save(this)
            }
        }
    }

    override fun noticeQing(qingjia:QingjiaEntity) {
//        val userlist:List<User> = act.departmentassist?.split(",").let { deplist->
//            mutableListOf<User>().let { userlist->
//                deplist?.forEach{
//                    departRepo.findDepByDepartmentname(it)?:throw RuntimeException("协同部门不存在")
//                    userlist.addAll(userRepo.findUsersByDepartments(it))
//                }
//                userlist.forEach(::println)
//                userlist
//            }
//        }
//        userlist.forEach{
            NoticeEntity(KeyUtil.getUniqueKey(),qingjia.vacateid,2,qingjia.vacateperson,0,"系统通知").apply {
                noticeRepo.save(this)
            }
//        }

    }

    override fun getuserlistBytype(type:String):List<User>?{
        if(type.length>2) {
             val mutableList = mutableListOf<User>().let {mutableList ->
                 departRepo.findById(type).orElse(null).apply {
                     val tmp = userRepo.findUsersByDepartments(this.departmentname)
                     mutableList.addAll(tmp)
                 }
                 mutableList
             }
            return mutableList
        }
        when(type){
            "1"->return userRepo.findUsersBySoftdelete(0)
            "2"->return userRepo.findUsersByRankAndSoftdelete("2",0)
            "3"->return userRepo.findUsersByRankAndSoftdelete("3",0)
            "4"->return userRepo.findUsersByRankAndSoftdelete("3",0).apply {
                val users:MutableList<User> = userRepo.findUsersByRankAndSoftdelete("2",0)
                this.addAll(users)
            }
        }
        return null
    }
}