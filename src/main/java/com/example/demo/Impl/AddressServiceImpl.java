package com.example.demo.Impl;

import com.example.demo.Entity.Address;
import com.example.demo.Mapper.AddressMapper;
import com.example.demo.Service.AddressService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;


    @Override
    public int addAddress(Address address) {
        return addressMapper.insertAddress(address);
    }


}
