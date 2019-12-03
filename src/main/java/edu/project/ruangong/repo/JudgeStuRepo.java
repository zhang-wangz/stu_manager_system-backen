package edu.project.ruangong.repo;

import edu.project.ruangong.dao.mapper.Judgestu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author athonyw
 * @version init
 * @date 2019/12/3 3:51 下午
 */
public interface JudgeStuRepo extends  JpaRepository<Judgestu,Integer> {
    Judgestu findJudgeStuByUid(String uid);
}
