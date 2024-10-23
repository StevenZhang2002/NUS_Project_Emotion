package com.example.demo.Mapper;

import com.example.demo.DTO.PointDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PointsMapper {

    @Insert("INSERT INTO points(userId, pointsBalance) VALUES (#{userId},800)")
    void initiatePoints(int userId);

    // 根据用户ID查询用户积分DTO
    @Select("select * from points where userId = #{userId}")
    PointDTO selectPointDTOByUserId(int userId);

    // 更新用户积分信息
    @Update("update points set pointsBalance = #{userPointsBalance} WHERE userId = #{userId} ")
    int updatePointsBalance(int userPointsBalance, int userId);
}
