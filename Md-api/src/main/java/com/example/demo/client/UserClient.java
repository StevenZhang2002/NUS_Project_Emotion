package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.transform.Result;


@FeignClient("User-service")
public interface UserClient {

    @GetMapping("/getUser/{id}")
    Result getUser(@PathVariable Integer id);
}