package edu.project.ruangong.repo;

import edu.project.ruangong.dao.mapper.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserBaseRepository extends JpaRepository<User, String> {

    List<User> findUsersByDepartments(String department);

    List<User> findUsersBySoftdelete(int softdelete);

    List<User> findUsersByDepartmentsAndSoftdelete(String department,int soft);

    List<User> findUsersByRank(String rank);

    List<User> findUsersByRankAndSoftdelete(String rank,int softdelete);

    @Query(value = "select  count(*) from user",nativeQuery = true)
    int coutusercount();

    @Query(value = "SELECT count(*) FROM user where DATE_SUB(CURDATE(), INTERVAL ? DAY) = date(appointment_time)",nativeQuery = true)
    int coutusercountbyinterval(int interval);
}
