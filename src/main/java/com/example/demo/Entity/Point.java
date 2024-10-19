package com.example.demo.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.security.Timestamp;

@Data
public class Point {

    private Integer pointsId;
    private Integer userId;
    private Integer pointsBalance = 0;
    private Timestamp updatedAt;

}