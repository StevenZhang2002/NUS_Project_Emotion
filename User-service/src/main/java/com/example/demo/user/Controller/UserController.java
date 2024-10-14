package com.example.demo.user.Controller;


import com.example.demo.common.Entity.User;
import com.example.demo.common.DTO.UserDTO;
import com.example.demo.common.Utils.JwtUtil;
import com.example.demo.common.Utils.Result;
import com.example.demo.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = "用户相关接口")
public class UserController {

    private final UserService userService;

    @ApiOperation("增加用户")
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

    @ApiOperation("登录功能")
    @GetMapping("/login")
    public Result login(@RequestParam String email, @RequestParam String password){
        User user = userService.getUserByEmail(email);
        if(user!=null){
            if(user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
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

    @ApiOperation("查询用户")
    @GetMapping("/getUser/{id}")
    public Result getUser(@PathVariable Integer id){
        User user = userService.getUser(id);
        return Result.success(user);
    }





//    @PostMapping("/changePassword")
//    public Result changePassword(@RequestParam int Email,
//                                 @RequestParam String oldPassword,
//                                 @RequestParam String newPassword){
//
//    }

}
