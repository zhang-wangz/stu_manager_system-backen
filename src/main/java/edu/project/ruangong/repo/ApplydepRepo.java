package edu.project.ruangong.repo;

import edu.project.ruangong.dao.mapper.Applydep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author athonyw
 * @version init
 * @date 2019/12/6 2:48 下午
 */

public interface ApplydepRepo extends JpaRepository<Applydep,String>{
    List<Applydep> findApplydepsByDepid(String depId);

    Applydep findApplydepByUid(String depId);
}
