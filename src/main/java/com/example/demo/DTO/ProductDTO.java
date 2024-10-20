package com.example.demo.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {

    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer pointsCost;
    private Integer stock;
}
