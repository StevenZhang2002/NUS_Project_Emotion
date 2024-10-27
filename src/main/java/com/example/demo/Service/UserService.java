package com.example.demo.Service;

import com.example.demo.Entity.User;

import java.util.List;

public interface UserService {
    public void addUser(String username, String password, String email, String gender, String status, byte[]avator);

    public User getUser(int id);

    public User getUserByEmail(String email);

    public List<User> getAllFriends(int userId);


}
