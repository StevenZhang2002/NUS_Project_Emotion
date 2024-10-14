package com.example.demo.friendship;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.example.demo.friendship.Mapper","com.example.demo.user.Service"})
@SpringBootApplication
public class FriendshipApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendshipApplication.class, args);
    }
}