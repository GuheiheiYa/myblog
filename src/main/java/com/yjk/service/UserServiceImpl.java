package com.yjk.service;

import com.yjk.dao.UserRepository;
import com.yjk.pojo.User;
import com.yjk.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findUserByUsernameAndPassword(username, MD5Utils.code(password));

        return user;
    }
}
