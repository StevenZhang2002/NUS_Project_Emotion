package com.example.demo.Controller;


import com.example.demo.Entity.Address;
import com.example.demo.Service.AddressService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "地址相关接口")
@RestController
@RequestMapping("/Address")
public class AddressController {
    @Autowired
    AddressService addressService;


    @Operation(summary = "添加地址")
    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody Address address) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        address.setUserId(userId);
        int addressId = addressService.addAddress(address);
        if(addressId>0){
            return Result.success(addressId);
        }
        return Result.error("Something wrong");
    }

    @Operation(summary = "根据地址id获取地址")
    @GetMapping("/{addressId}")
    public Result getAddressById(@PathVariable int addressId) {
        Address address = addressService.getAddressById(addressId);
        if(address!=null){
            return Result.success(address);
        }
        return Result.error("Invalid Address");
    }


    @Operation(summary = "删除地址")
    @DeleteMapping("/deleteAddress")
    public Result deleteAddress(@RequestParam int addressId) {
        Address address = addressService.getAddressById(addressId);
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        if(address!=null){
            if(address.getUserId()!=userId){
                return Result.error("Not Related Account");
            }
            addressService.deleteAddressById(addressId);
            return Result.success("deleting address success");
        }
        return Result.error("Invalid Address");
    }


    @Operation(summary = "获取当前用户的所有地址")
    @GetMapping("/addresses")
    public Result getAddressByUserId(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        List<Address>addresses = addressService.getAddressByUserId(userId);
        return Result.success(addresses);
    }





}
