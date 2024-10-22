package com.example.demo.Service;

import com.example.demo.Entity.User;

public interface UserService {
    public int addUser(String username, String password, String email, String gender, String status, byte[]avator);

    public User getUser(int id);

    public User getUserByEmail(String email);


}
