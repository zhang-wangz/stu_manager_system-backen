package edu.project.ruangong.service.impl;

import edu.project.ruangong.dao.mapper.User;
import edu.project.ruangong.enums.UserEnum;
import edu.project.ruangong.exception.UserException;
import edu.project.ruangong.repo.UserBaseRepository;
import edu.project.ruangong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserBaseRepository userBaseRepository;

    @Override
    public List<User> findAll() {
        return userBaseRepository.findAll();
    }

    @Override
    public User register(User user) {
        if(user == null)
            throw  new UserException(UserEnum.CREATE_FAIL_BLANK);

        userBaseRepository.save(user);
        return user;
    }

    @Override
    public User findOne(String stuid) {
        return userBaseRepository.findById(stuid).orElse(null);
    }

    @Override
    public User login(String uid, String pwd) {
        User user = userBaseRepository.findById(uid).orElse(null);
        if(user==null){
            throw new UserException(UserEnum.USER_UID_BLANK.getCode(),"学号输入错误");
        }
        else{
           if(pwd.equals(user.getPwd())) return user;
           else throw new UserException(UserEnum.USER_PWD_BLANK.getCode(),"用户密码不正确");
        }
    }

    @Override
    public void save(User user) {
        User user1 = userBaseRepository.findById(user.getUid()).orElse(null);
        if(user1 != null){
            throw  new UserException(-4,"用户名已存在");
        }
        else userBaseRepository.save(user);
    }
}
