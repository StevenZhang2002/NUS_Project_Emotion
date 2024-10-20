package com.example.demo.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.security.Timestamp;

@Data
public class PointsTransaction {

    private Integer transactionId;
    private Integer userId;
    private Integer changeAmount;
    private TransactionType transactionType;
    private String description;
    private Timestamp createdAt;

    public enum TransactionType {
        EARN, SPEND
    }
}
