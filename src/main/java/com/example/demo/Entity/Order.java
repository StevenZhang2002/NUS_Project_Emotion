package com.example.demo.Entity;

import lombok.Data;

import java.security.Timestamp;

@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer totalPoints;
    private Timestamp createdAt;
}
