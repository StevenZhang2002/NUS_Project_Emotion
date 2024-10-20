package com.example.demo.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDTO {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer totalPoints;
    private Integer addressId;
}
