package com.example.demo.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddressDTO {
    private Integer addressId;
    private Integer userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
