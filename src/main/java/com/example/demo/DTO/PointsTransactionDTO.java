package com.example.demo.DTO;

import java.sql.Timestamp;

public class PointsTransactionDTO {
    private Integer transactionId;
    private Integer userId;
    private Integer changeAmount;
    private String transactionType;
    private String description;
    private Timestamp createdAt;
}
