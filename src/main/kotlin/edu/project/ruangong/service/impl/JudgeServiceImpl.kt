package edu.project.ruangong.service.impl

import edu.project.ruangong.dao.mapper.JudgeEntity
import edu.project.ruangong.repo.JudgeRepo
import edu.project.ruangong.service.JudgeService
import org.springframework.stereotype.Service

/**
 * @author  athonyw
 * @date  2019/11/17 8:09 下午
 * @version init
 */
@Service
class JudgeServiceImpl(var judgRepo:JudgeRepo):JudgeService{
    override fun judge(judgId: String?, isJudge: Int?):JudgeEntity{
        judgId?:throw RuntimeException("judgeid不可为空") as Throwable
        isJudge?:throw RuntimeException("isJudge不可为空")
        val judge = judgRepo.findById(judgId).orElse(null)?.apply {
            this.isjudge = isJudge
            judgRepo.save(this)
        }?:throw RuntimeException("judgeid输入错误")
        return judge
    }
}