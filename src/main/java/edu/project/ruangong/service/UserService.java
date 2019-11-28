package edu.project.ruangong.service;

import edu.project.ruangong.dao.mapper.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService{
    List<User> findAll();
    User register(User user);
    User findOne(String uid);
    User login(String uid,String pwd);
    void save(User user);
}
