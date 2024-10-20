package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;

import java.util.List;

public interface FriendshipService {
    public User getUserByEmail(String email);

    public void buildFriendship(int userId1, int userId2);

    public Friendship getFriendship(int friendshipId);

    public Friendship getFriendshipByTwoUserId(int userId1, int userId2);

    public List<UserDTO> getFollowingList(Integer userId1);
}
