package com.example.demo.friendship.Impl;

import com.example.demo.common.Entity.Friendship;
import com.example.demo.friendship.Mapper.FriendshipMapper;
import com.example.demo.friendship.Service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipMapper friendshipMapper;

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
}
