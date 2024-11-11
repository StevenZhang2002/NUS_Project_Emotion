package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer pointsCost;
    private Integer stock;
    private String image;
}
