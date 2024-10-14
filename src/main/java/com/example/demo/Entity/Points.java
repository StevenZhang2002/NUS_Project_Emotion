package com.example.demo.Entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Points {
    private Integer pointsId;
    private Integer userId; // 外键
    private Integer pointsBalance;
    private Timestamp updatedAt;
}
