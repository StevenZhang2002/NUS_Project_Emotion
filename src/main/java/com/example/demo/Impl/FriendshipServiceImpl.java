package com.example.demo.Impl;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.FriendshipMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    @Autowired
    FriendshipMapper friendshipMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public void buildFriendship(int userId1, int userId2) {
        friendshipMapper.BuildRelationship(userId1,userId2);
    }

    @Override
    public Friendship getFriendship(int friendshipId) {
        return friendshipMapper.GetFriendship(friendshipId);
    }

    @Override
    public Friendship getFriendshipByTwoUserId(int userId1, int userId2) {
        return friendshipMapper.GetFriendshipByTwoUserId(userId1,userId2);
    }

    @Override
    public List<UserDTO> getFollowingList(Integer userId) {
        // 从数据库中获取该用户关注的用户列表
        return friendshipMapper.getFollowingListByUserId(userId);
    }


}
