package com.example.demo.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {
    @Insert("INSERT INTO likes (postId, userId) VALUES (#{postId},#{postId})")
    public void addLike(int postId, int userId);
}
