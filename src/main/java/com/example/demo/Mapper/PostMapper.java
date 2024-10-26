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

    //获取当前页的结果列表
    @Select("SELECT p.postId, p.recordId, p.userId, r.title, r.content, r.mood, r.createdAt, r.updatedAt, r.topEmotion, r.comfortLanguage, r.behavioralGuidance " +
            "FROM post p " +
            "JOIN record r ON p.recordId = r.recordId " +
            "ORDER BY p.createdAt DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<PostDTO> list(Integer start, Integer pageSize);


    //插入数据
    @Insert("insert into post(recordId,userId) values (#{recordId},#{recordId})")
    void insertPost(Post post);
}
