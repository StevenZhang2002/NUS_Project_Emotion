package com.example.demo.Mapper;


import com.example.demo.Entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface AddressMapper {

    @Insert("INSERT INTO Address (userId, street, city, state, postalCode, country, createdAt, updatedAt) " +
            "VALUES (#{userId}, #{street}, #{city}, #{state}, #{postalCode}, #{country}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "addressId")
    int insertAddress(Address address);
}
