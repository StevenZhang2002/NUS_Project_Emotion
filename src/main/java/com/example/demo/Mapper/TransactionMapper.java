package com.example.demo.Mapper;

import com.example.demo.DTO.PointsTransactionDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionMapper {
    // 插入积分交易记录
    @Insert("INSERT INTO PointsTransaction (userId, changeAmount, transactionType, description) " +
            "VALUES (#{userId}, #{changeAmount}, #{transactionType}, #{description})")
    int insertTransaction(PointsTransactionDTO transaction);

    @Select("select count(*) from post")
    public Long count();


    @Select("SELECT * FROM PointsTransaction where userId = #{userId} order by createdAt desc limit #{start}, #{pageSize}")
    public List<PointsTransactionDTO>list(int userId, int start, int pageSize);
}
