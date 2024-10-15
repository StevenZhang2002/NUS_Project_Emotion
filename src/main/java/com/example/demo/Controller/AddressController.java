package com.example.demo.Controller;


import com.example.demo.Entity.Address;
import com.example.demo.Service.AddressService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





}
