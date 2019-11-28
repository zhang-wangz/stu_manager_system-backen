package edu.project.ruangong.controller;

import edu.project.ruangong.dao.mapper.User;
import edu.project.ruangong.dto.UserDto;
import edu.project.ruangong.enums.UserEnum;
import edu.project.ruangong.form.UserEditInfoExcpSec;
import edu.project.ruangong.form.UserEditSerect;
import edu.project.ruangong.form.UserFormLogin;
import edu.project.ruangong.form.UserFormRegister;
import edu.project.ruangong.repo.DepartRepo;
import edu.project.ruangong.repo.UserBaseRepository;
import edu.project.ruangong.service.UserService;
import edu.project.ruangong.utils.Md5Utils;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBaseRepository userBaseRepository;

    @Autowired
    private DepartRepo departRepo;

    @PostMapping("/register")
    public ResultVO create(@Valid UserFormRegister userFormRegister,
                           BindingResult bindingResult) {
        int flag = 1;
        String msg = "fail";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            flag = -1;
        }
        if (flag == 1) {
            try {
                User user = new User();
                userFormRegister.setPwd(Md5Utils.MD5EncodeUtf8(userFormRegister.getPwd()));
                BeanUtils.copyProperties(userFormRegister, user);
                if(departRepo.findDepartmentByDepartmentname(userFormRegister.getDepartments())==null)
                    return ResultUtil.error(-4,"所属部门不存在");
                userService.save(user);
                return ResultUtil.success();
            } catch (Exception e) {
                return ResultUtil.error(-4, e.getMessage());
            }
        } else {
            return ResultUtil.error(UserEnum.CREATE_FAIL_BLANK.getCode(), msg);
        }
    }

    @PostMapping(path = "/login")
    public ResultVO login(@Valid UserFormLogin userFormLogin,
                          BindingResult bindingResult) {
        Integer flag = 1;
        String msg = "fail";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            flag = -1;
        }
        if (flag == 1) {
            try {
                userService.login(userFormLogin.getUid(), Md5Utils.MD5EncodeUtf8(userFormLogin.getPwd()));
            } catch (Exception e) {
                return ResultUtil.error(-4, e.getMessage());
            }
        } else {
            return ResultUtil.error(UserEnum.CREATE_FAIL_BLANK.getCode(), msg);
        }
        User user = userBaseRepository.findById(userFormLogin.getUid()).orElse(null);
        user.setActivecode(1);
        userBaseRepository.save(user);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
//        String JWT = JwtUtils.createJWT(userDto.getUid(), userDto.getUsername(), SystemConstant.JWT_TTL);
//        response.setHeader("token",JWT);
        return ResultUtil.success(userDto);
    }

    @PostMapping(path = "/exitUser")
    public ResultVO exitUser(@RequestBody Map<String,String> map){
        System.out.println(map.get("uid").toString());
        User user = userBaseRepository.findById(map.get("uid").toString()).orElse(null);
        user.setActivecode(0);
        userBaseRepository.save(user);
        return ResultUtil.success(0,user.getUsername()+"已退出");
    }


    @GetMapping(path = "/resetSec")
    public ResultVO resetSec(@RequestParam(value = "uid") String uid) {
        User user = null;
        user = userBaseRepository.findById(uid).orElse(null);
        if (user == null) return ResultUtil.error(-5, "该学号不存在系统中，请注册");
        else user.setPwd(Md5Utils.MD5EncodeUtf8("zucc"));
        userBaseRepository.save(user);
        return ResultUtil.success(0, "pwd已经重置完毕");
    }


    @PostMapping("/userinfoedit")
    public ResultVO userinfoedit(@Valid UserEditInfoExcpSec userEditInfoExcpSec,
                                 BindingResult bindingResult) {
        int flag = 1;
        String msg = "fail";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            flag = -1;
        }
        if (flag == 1) {
            try {
                User user = null;
                user = userBaseRepository.findById(userEditInfoExcpSec.getUid()).orElse(null);
                if (user == null) return ResultUtil.error(-5, "该学号不存在系统中，请注册");
                User user1 = new User();
                BeanUtils.copyProperties(userEditInfoExcpSec, user1);
                if(departRepo.findDepartmentByDepartmentname(userEditInfoExcpSec.getDepartments())==null)
                    return ResultUtil.error(-4,"所属部门不存在");
                user1.setPwd(user.getPwd());
                userBaseRepository.save(user1);
                return ResultUtil.success(userEditInfoExcpSec);
            } catch (Exception e) {
                return ResultUtil.error(-6, e.getMessage());
            }
        } else {
            return ResultUtil.error(UserEnum.EDIT_INFO_WRONG.getCode(), msg);
        }
    }

    @PostMapping("/userSecedit")
    public ResultVO resetSec(@Valid UserEditSerect userEditSerect,
                             BindingResult bindingResult) {
        int flag = 1;
        String msg = "fail";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            flag = -1;
        }
        if (flag == 1) {
            try {
                User user = null;
                user = userBaseRepository.findById(userEditSerect.getUid()).orElse(null);
                if (user == null) return ResultUtil.error(-5, "该学号不存在系统中，请注册");

                if(userEditSerect.getOldpwd().equals(userEditSerect.getNewpwd())){
                    return ResultUtil.error(-5,"旧密码和新密码不能一致");
                }

                if (Md5Utils.MD5EncodeUtf8(userEditSerect.getOldpwd()).equals(user.getPwd())) {
                    user.setPwd(Md5Utils.MD5EncodeUtf8(userEditSerect.getNewpwd()));
                    userBaseRepository.save(user);
                } else {
                    return ResultUtil.error(-5, "旧密码输入错误");
                }

                return ResultUtil.success(0, "密码修改成功");
            } catch (Exception e) {
                return ResultUtil.error(-5, e.getMessage());
            }
        } else {
            return ResultUtil.error(UserEnum.EDIT_INFO_WRONG.getCode(), msg);
        }
    }


    @GetMapping("/getallusr")
    public ResultVO getallusr(){
        return ResultUtil.success(userBaseRepository.findUsersBySoftdelete(0));
    }

    @GetMapping("softdeleteuser")
    public ResultVO softdeleteuser(@RequestParam(value = "userId")String userId){
        User user = userBaseRepository.findById(userId).orElse(null);
        user.setSoftdelete(1);
        userBaseRepository.save(user);
        return ResultUtil.success(0,"success left");
    }

    @GetMapping("getusrinfobyId")
    public ResultVO getallusr(@RequestParam(value = "userId")String userId){
        User user = userBaseRepository.findById(userId).orElse(null);
        if(user==null) return ResultUtil.error(-5, "该学号不存在系统中，请注册");
        UserEditInfoExcpSec editInfoExcpSec = new UserEditInfoExcpSec();
        BeanUtils.copyProperties(user,editInfoExcpSec);
        return ResultUtil.success(editInfoExcpSec);
    }

    @GetMapping("getAllministerid")
    public ResultVO getAllministerid(){
        return ResultUtil.success(userBaseRepository.findUsersByRank("1"));
    }

    @GetMapping("getchairandtea")
    public ResultVO getchairandtea(){
        java.util.Map map = new HashMap();
        map.put("主席",userBaseRepository.findUsersByRank("5"));
        map.put("副主席",userBaseRepository.findUsersByRank("4"));
        map.put("指导老师",userBaseRepository.findUsersByRank("6"));
        return ResultUtil.success(map);
    }

}
