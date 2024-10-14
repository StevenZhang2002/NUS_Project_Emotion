package com.example.demo.friendship;

import com.example.demo.client.UserClient;
import com.example.demo.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan({"com.example.demo.friendship.Mapper"})
@EnableFeignClients(basePackages = "com.example.demo.client", defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
public class FriendshipApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendshipApplication.class, args);
    }
}