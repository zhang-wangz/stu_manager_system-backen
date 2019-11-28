package edu.project.ruangong.repo;

import edu.project.ruangong.dao.mapper.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DepartRepo extends JpaRepository<Department,String>{
    List<Department> findDepartmentByDepartmentid(String departid);

//    @Query(value = "SELECT departmentname FROM department where departmentname LIKE %?1% ",nativeQuery = true)
    List<Department> findDepartmentByDepartmentidContaining(String departid);

    List<Department> findDepartmentByDepartmentname(String departName);
    List<Department> findDepartmentByDepartmentnameContaining(String departName);

    @Query(value = "SELECT * FROM department where departmentname = ?1",nativeQuery = true)
    Department findDepByDepartmentname(String departName);

    @Query(value = "SELECT departmentname FROM department group by departmentname",nativeQuery = true)
    List<String> findDepartmentsName();

    List<Department> findDepartmentsByChairmanid(String chairId);


}
