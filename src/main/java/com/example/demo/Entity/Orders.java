package com.example.demo.Entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Integer orderId;
    private Integer userId; // 外键
    private Integer productId; // 外键
    private Integer quantity; // 兑换数量
    private Integer totalPoints; // 总共花费积分
    private String orderStatus;
    private Timestamp createdAt;
}
