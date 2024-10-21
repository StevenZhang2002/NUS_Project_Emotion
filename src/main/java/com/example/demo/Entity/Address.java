package com.example.demo.Entity;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer addressId;
    private Integer userId;
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String postalCode;
    @NotEmpty
    private String country;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;


}
