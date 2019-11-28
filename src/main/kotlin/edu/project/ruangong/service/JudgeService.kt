package edu.project.ruangong.service

import edu.project.ruangong.dao.mapper.JudgeEntity
import org.springframework.stereotype.Service

/**
 * @author  athonyw
 * @date  2019/11/17 8:05 下午
 * @version init
 */
@Service
interface JudgeService {
    fun judge(judgId:String?,isJudge:Int?):JudgeEntity
}