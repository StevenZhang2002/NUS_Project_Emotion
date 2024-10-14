package com.example.demo.Entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsTransaction {
    private Integer transactionId;
    private Integer userId; // 外键
    private Integer changeAmount; // 积分变动，正值增加，负值减少
    private String transactionType;  // 'Earn' or 'Spend'
    private String description; // 如”发帖赚取积分“或”兑换商品“
    private Timestamp createdAt;
}
