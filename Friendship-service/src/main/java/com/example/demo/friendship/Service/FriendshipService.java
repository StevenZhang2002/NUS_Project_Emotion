package com.example.demo.friendship.Service;

import com.example.demo.common.Entity.Friendship;

public interface FriendshipService {
    public void buildFriendship(int userId1, int userId2);

    public Friendship getFriendship(int friendshipId);

    public Friendship getFriendshipByTwoUserId(int userId1, int userId2);
}
