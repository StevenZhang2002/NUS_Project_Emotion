package com.example.demo.Impl;

import com.example.demo.Entity.Likes;
import com.example.demo.Mapper.PostLikeMapper;
import com.example.demo.Service.LikeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeImpl implements LikeInterface {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    String userIdPrefix = "userId:";
    String postIdPrefix = "postId:";



    @Transactional
    @Override
    public Likes addLike(int postId, int userId) {
        String userLikedKey = userIdPrefix + userId;
//       如果redis中显示已经点过赞了，就无事发生
        if(redisTemplate.opsForSet().isMember(userLikedKey, postId)) {
            return null;
        }
        else{
//            <userLikedKey:[postId1, postId2]>
            redisTemplate.opsForSet().add(userLikedKey, postId);
//            <postLikedKey:[count:num]>
            redisTemplate.opsForHash().increment(postIdPrefix+postId,"count",1);
            return new Likes(postId,userId,true);
        }
    }
}
