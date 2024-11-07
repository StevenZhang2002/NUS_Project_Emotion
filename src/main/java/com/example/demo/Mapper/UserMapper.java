package com.example.demo.Mapper;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.Result;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserMapper{
    @Insert("INSERT INTO tb_user(Username,Password,gender,email,status,avator) VALUES (#{username},#{password},#{gender},#{email},#{status},#{avator})")
    void addUser(String username, String password, String email, String gender, String status, byte[] avator);

    @Select("SELECT * FROM tb_user WHERE userId = #{userId}")
    User getUserById(int userId);


    @Select("SELECT * FROM tb_user WHERE email = #{email}")
    User getUserByEmail(String email);

    @Select("SELECT u.* FROM tb_user u " +
            "JOIN friendship f ON (f.userId1 = #{userId} AND f.userId2 = u.userId) OR " +
            "(f.userId2 = #{userId} AND f.userId1 = u.userId)")
    List<User> getAllFriends(int userId);

    @Update({
            "<script>",
            "UPDATE tb_user",
            "<set>",
            "<if test='username != null'>Username = #{username},</if>",
            "<if test='email != null'>email = #{email},</if>",
            "<if test='gender != null'>gender = #{gender},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "<if test='avator != null'>avator = #{avator},</if>",
            "</set>",
            "WHERE userId = #{userId}",
            "</script>"
    })
    void updateUser(int userId, String username, String email, String gender, String status, byte[] avator);

    @Delete("DELETE FROM tb_user WHERE userId = #{userId}")
    void deleteUser(int userId);

}
