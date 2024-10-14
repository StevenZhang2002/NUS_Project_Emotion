package com.example.demo.Entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer pointsCost; // 兑换商品所需积分
    private Integer stock; // 商品库存
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
