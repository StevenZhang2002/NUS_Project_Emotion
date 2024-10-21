package com.example.demo.Controller;


import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.UserLoginDTO;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.JwtUtil;
import com.example.demo.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.Utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.util.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@Tag(name = "用户相关接口")


/**
 * 用户名加密
 *
 */
public class UserController {



    @Autowired
    private UserService userService;


//    @PostMapping("/ResetPwd")
//    public Result ResetPwd() {
//        Map<String, Object> result = ThreadLocalUtil.get();
//    }


    @Operation(summary = "增加用户")
    @PostMapping("/addUser")
    public Result addUser(@ModelAttribute @Validated UserDTO userDTO, @RequestParam MultipartFile avatorPic) throws IOException {
        byte[]avator = avatorPic.getBytes();
        if(userService.getUserByEmail(userDTO.getEmail())!=null){
            return Result.error("Account already exist");
        }
        String encryptedPassword = DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes());
        userService.addUser(userDTO.getUsername(), encryptedPassword, userDTO.getEmail(), userDTO.getGender(), userDTO.getStatus(), avator);
        return Result.success();
    }

    @Operation(summary = "登录功能")
    @PostMapping("/login")
    public Result login(@ModelAttribute UserLoginDTO userDto){
        User user = userService.getUserByEmail(userDto.getEmail());
        if(user!=null){
            if(user.getPassword().equals(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()))){
                Map<String, Object>claims = new HashMap<>();
                claims.put("email", user.getEmail());
                claims.put("id",user.getUserId());
                String token = JwtUtil.genToken(claims);
                return Result.success(token);
            }
            else{
                return Result.error("please recheck the email/Password");
            }
        }
        return Result.error("Invalid Account");
    }





//    @PostMapping("/changePassword")
//    public Result changePassword(@RequestParam int Email,
//                                 @RequestParam String oldPassword,
//                                 @RequestParam String newPassword){
//
//    }

}
