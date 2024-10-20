package com.example.demo.Mapper;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FriendshipMapper {

    @Select("SELECT * FROM tb_user WHERE email = #{email}")
    User getUserByEmail(String email);

    @Insert("INSERT INTO friendship(userId1,userId2) VALUES (#{userId1},#{userId2})")
    public void BuildRelationship(int userId1, int userId2);

    @Select("SELECT * from friendship where friendshipId = #{friendshipId}")
    public Friendship GetFriendship(int friendshipId);

    @Select("SELECT * FROM friendship where (userId1 = #{userId1} AND userId2 = #{userId2}) OR (userId1 = #{userId2} AND userId2 = #{userId1})")
    public Friendship GetFriendshipByTwoUserId(int userId1, int userId2);

    @Select("SELECT t.username,t.gender,t.email FROM friendship f " +
            "JOIN tb_user t ON f.userId2 = t.userId " +
            "WHERE f.userId1 = #{userId}")
    List<UserDTO> getFollowingListByUserId(Integer userId);

}
