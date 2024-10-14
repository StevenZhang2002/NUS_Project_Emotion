package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.common.Utils.Result;
//import javax.xml.transform.Result;


@FeignClient("User-service")
public interface UserClient {

    @GetMapping("/users/getUser/{id}")
    Result getUser(@PathVariable("id") Integer id);
}