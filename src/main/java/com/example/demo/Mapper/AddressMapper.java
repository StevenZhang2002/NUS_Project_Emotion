package com.example.demo.Mapper;


import com.example.demo.Entity.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {

    @Insert("INSERT INTO Address (userId, street, city, state, postalCode, country, createdAt, updatedAt) " +
            "VALUES (#{userId}, #{street}, #{city}, #{state}, #{postalCode}, #{country}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "addressId")
    int insertAddress(Address address);


    @Select("SELECT * FROM Address WHERE addressId = #{addressId}")
    Address getAddressById(int addressId);


    @Delete("DELETE FROM Address WHERE addressId = #{id}")
    void deleteAddressById(int id);

    @Select("SELECT * FROM Address WHERE userId = #{userId}")
    List<Address> getAddressByUserId(int userId);

}
