package com.example.demo.user.Impl;

import com.example.demo.common.Entity.User;
import com.example.demo.user.Mapper.UserMapper;
import com.example.demo.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void addUser(String username, String password, String email, String gender, String status, byte[] avator) {
        userMapper.addUser(username, password, email, gender, status, avator);

    }

    @Override
    public User getUser(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
