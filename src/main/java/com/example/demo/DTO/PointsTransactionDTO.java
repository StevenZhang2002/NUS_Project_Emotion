package com.example.demo.DTO;

import lombok.Data;

import java.io.Serializable;


@Data

public class PointsTransactionDTO implements Serializable {
    private Integer transactionId;
    private Integer userId;
    private Integer changeAmount;
    private String transactionType;
    private String description;
}
