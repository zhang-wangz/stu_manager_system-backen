package edu.project.ruangong.repo;

import edu.project.ruangong.dao.mapper.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserBaseRepository extends JpaRepository<User, String> {

    List<User> findUsersByDepartments(String department);

    List<User> findUsersBySoftdelete(int softdelete);

    List<User> findUsersByDepartmentsAndSoftdelete(String department,int soft);

    List<User> findUsersByRank(String rank);

    List<User> findUsersByRankAndSoftdelete(String rank,int softdelete);
}
