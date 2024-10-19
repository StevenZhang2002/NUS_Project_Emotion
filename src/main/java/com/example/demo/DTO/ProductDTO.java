package com.example.demo.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductDTO {
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer pointsCost;
    private Integer stock;
}
