package com.example.demo.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PointDTO {

    private Integer pointsId;
    private Integer userId;
    private Integer pointsBalance;


}