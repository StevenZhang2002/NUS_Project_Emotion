package com.example.demo.common.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
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
}
