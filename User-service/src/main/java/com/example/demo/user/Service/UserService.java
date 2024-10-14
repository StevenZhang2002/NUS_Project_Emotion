package com.example.demo.user.Service;

import com.example.demo.common.Entity.User;

public interface UserService {
    public void addUser(String username, String password, String email, String gender, String status, byte[]avator);

    public User getUser(int id);

    public User getUserByEmail(String email);


}
