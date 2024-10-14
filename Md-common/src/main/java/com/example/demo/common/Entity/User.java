package com.example.demo.common.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class User {
    Integer userId;
    @NotEmpty(message = "username is required")
    String username;
    @NotEmpty(message = "Password is required")
    String password;
    @NotEmpty
    String gender;
    @NotEmpty(message = "please input the email")
    @Email
    String email;
    @NotEmpty
    String status;
    Timestamp createdAt;
    Timestamp updatedAt;
    @NotEmpty
    private byte[]avator;
}
