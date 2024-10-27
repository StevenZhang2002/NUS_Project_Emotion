package com.example.demo.Mapper;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    //获取总记录数
    @Select("select count(*) from post")
    public Long count();

    @Select("SELECT p.postId AS postId, p.recordId AS recordId, p.userId AS userId, r.title AS title, r.content AS content, " +
            "r.mood AS mood, r.createdAt AS createdAt, r.updatedAt AS updatedAt, r.topEmotion AS topEmotion, " +
            "r.comfortLanguage AS comfortLanguage, r.behavioralGuidance AS behavioralGuidance " +
            "FROM post p " +
            "JOIN record r ON p.recordId = r.recordId " +
            "ORDER BY p.createdAt DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<PostDTO> list(Integer start, Integer pageSize);


    //插入数据
    @Insert("insert into post(recordId,userId) values (#{recordId},#{userId})")
    void insertPost(Post post);
}
