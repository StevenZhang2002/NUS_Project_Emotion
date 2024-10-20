package com.example.demo.Mapper;

import com.example.demo.DTO.OrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface PayOrderMapper {

    // 插入支付单记录
    @Insert("insert into orders (userId, productId, quantity, totalPoints, addressId) " +
            "values (#{userId}, #{productId}, #{quantity}, #{totalPoints},#{addressId})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insertOrder(OrderDTO orderDTO);
}
