package com.example.demo.Service;

import com.example.demo.Entity.Address;

import java.util.List;

public interface AddressService {
    public int addAddress(Address address);

    public Address getAddressById(int id);

    public void deleteAddressById(int id);

    public List<Address> getAddressByUserId(int userId);

}
