package edu.project.ruangong.controller;

import edu.project.ruangong.dao.mapper.Department;
import edu.project.ruangong.dao.mapper.User;
import edu.project.ruangong.enums.DepartEnum;
import edu.project.ruangong.exception.DepartException;
import edu.project.ruangong.form.Departform;
import edu.project.ruangong.repo.DepartRepo;
import edu.project.ruangong.repo.UserBaseRepository;
import edu.project.ruangong.utils.KeyUtil;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/depart")
public class DepartControl {
    @Autowired
    private DepartRepo departRepo;

    @Autowired
    private UserBaseRepository userBaseRepository;

    @PostMapping("/addDepart")
    public ResultVO addDepart(@Valid Departform departform,
                              BindingResult bindingResult){
        int flag = 1;
        String msg = "fail";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            flag = -1;
        }
        if(flag==1){
            try {
                Department department = new Department();
                String uniquid = KeyUtil.getUniqueKey();
                departform.setDepartmentid(uniquid);
                BeanUtils.copyProperties(departform, department);
                if(userBaseRepository.findById(departform.getChairmanid()).orElse(null)==null)
                    throw new DepartException("部长学号错误");
                if(userBaseRepository.findById(departform.getChairmanid()).orElse(null)==null)
                    throw new DepartException("分管主席学号错误");
                departRepo.save(department);
                return ResultUtil.success(department);
            } catch (Exception e) {
                return ResultUtil.error(-4, e.getMessage());
            }
        } else {
            return ResultUtil.error(DepartEnum.INFO_DEPART_FAIL.getCode(), msg);
        }
    }

    @GetMapping("deleteDepart")
    public ResultVO deleteDepart(@RequestParam(name = "depId")String departId){
        Department department = departRepo.findById(departId).orElse(null);
        if(department==null) return ResultUtil.error(-2,"所要删除的部门id输入错误");
        try{
            department.setDeletetime(new Date(System.currentTimeMillis()));
            departRepo.save(department);
        }catch (Exception e){
            return ResultUtil.error(-4, e.getMessage());
        }
        return ResultUtil.success(0,"注销成功");
    }


    //copy from add
    @PostMapping("/departinfoedit")
    public ResultVO departinfoedit(@Valid Departform departform,
                                 BindingResult bindingResult) {
        int flag = 1;
        String msg = "fail";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            flag = -1;
        }
        if(flag==1){
            try {
                Department department = new Department();
                BeanUtils.copyProperties(departform, department);
                if(departform.getDepartmentid()==null) return ResultUtil.error(DepartEnum.BACK_WRONG.getCode(),"编辑时部门id为空，请重新提交");
                if(userBaseRepository.findById(departform.getMinisterid()).orElse(null)==null)
                    throw new DepartException("部长学号错误");
                if(userBaseRepository.findById(departform.getChairmanid()).orElse(null)==null)
                    throw new DepartException("分管主席学号错误");
                departRepo.save(department);
                return ResultUtil.success(department);
            } catch (Exception e) {
                return ResultUtil.error(-4, e.getMessage());
            }
        } else {
            return ResultUtil.error(DepartEnum.INFO_DEPART_FAIL.getCode(), msg);
        }
    }

    @GetMapping("searchBydepNamelike")
    public ResultVO searchBydepNamelike(@RequestParam(name = "depName")String depName){
        List<Department> department = departRepo.findDepartmentByDepartmentnameContaining(depName);
        if(department==null) return ResultUtil.error(-1,"查询结果为空");

        return ResultUtil.success(department);
    }

    @GetMapping("searchBydepName")
    public ResultVO searchBydepName(@RequestParam(name = "depName")String depName){
        List<Department> department = departRepo.findDepartmentByDepartmentname(depName);
        if(department==null) return ResultUtil.error(-1,"查询结果为空");

        return ResultUtil.success(department);
    }

    @GetMapping("searchBydeIdlike")
    public ResultVO searchBydeIdlike(@RequestParam(name = "depId")String depId){
        List<Department> department = departRepo.findDepartmentByDepartmentidContaining(depId);
        if(department==null) return ResultUtil.error(-1,"查询结果为空");

        return ResultUtil.success(department);
    }

    @GetMapping("searchBydeId")
    public ResultVO searchBydeId(@RequestParam(name = "depId")String depId){
        List<Department> department = departRepo.findDepartmentByDepartmentid(depId);
        if(department==null) return ResultUtil.error(-1,"查询结果为空");
        return ResultUtil.success(department);
    }


    @GetMapping("getdepList")
    public ResultVO getdepList(){
        List<Department> youke = departRepo.findDepartmentByDepartmentname("暂无");
        List<Department> list = departRepo.findAll();
        list.remove(youke.get(0));

        List<Departform> departformList = new ArrayList<>();
        for(Department each:list){
            Departform departform = new Departform();
            BeanUtils.copyProperties(each,departform);
            departform.setChairmanname((Objects.requireNonNull(userBaseRepository.findById(each.getChairmanid()).orElse(null))).getUsername());
            departform.setMinistername((Objects.requireNonNull(userBaseRepository.findById(each.getMinisterid()).orElse(null))).getUsername());
            departformList.add(departform);
        }

        return ResultUtil.success(departformList);
    }

    @GetMapping("getDepBydepId")
    public ResultVO getDepBydepId(@RequestParam(value = "depId") String depId){
        Department department = departRepo.findById(depId).orElse(null);
        if(department==null) return  ResultUtil.error(DepartEnum.BACK_WRONG.getCode(),DepartEnum.BACK_WRONG.getMsg());
        return  ResultUtil.success(department);
    }

    @GetMapping("getdeNamepList")
    public ResultVO getdeNamepList(){
        List<Department> youke = departRepo.findDepartmentByDepartmentname("暂无");
        List<String> list = departRepo.findDepartmentsName();
        list.remove(youke.get(0).getDepartmentname());
        return ResultUtil.success(list);
    }

    @GetMapping("getdepMenberById")
    public ResultVO getdepMenberbyId(@RequestParam(value = "depId") String depId){
        Department department = departRepo.findById(depId).orElse(null);
        if(department==null) return  ResultUtil.error(DepartEnum.BACK_WRONG.getCode(),DepartEnum.BACK_WRONG.getMsg());
        List<User> userlist = userBaseRepository.findUsersByDepartmentsAndSoftdelete(department.getDepartmentname(),0);
        return ResultUtil.success(userlist);
    }

    @GetMapping("getdepMenberByfenguan")
    public ResultVO getdepMenberByfenguan(@RequestParam(value = "chairId") String fenguanId){
        List users = new ArrayList<>();
        List<Department> fenguandep = departRepo.findDepartmentsByChairmanid(fenguanId);
        for(Department each:fenguandep){
            users.add(getdepMenberbyId(each.getDepartmentid()).getDataObj());
        }
        return ResultUtil.success(users);
    }


    @GetMapping("getdepInfoByfenguan")
    public ResultVO getdepInfoByfenguan(@RequestParam(value = "chairId") String fenguanId){
        List users = new ArrayList<>();
        List<Department> fenguandep = departRepo.findDepartmentsByChairmanid(fenguanId);
//        for(Department each:fenguandep){
//            users.add(getdepMenberbyId(each.getDepartmentid()).getDataObj());
//        }
        return ResultUtil.success(fenguandep);
    }


}
