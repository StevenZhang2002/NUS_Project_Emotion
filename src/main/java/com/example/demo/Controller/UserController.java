package com.example.demo.Controller;


import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.UserLoginDTO;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.AddressMapper;
import com.example.demo.Mapper.PointsMapper;
import com.example.demo.Service.PointsService;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.JwtUtil;
import com.example.demo.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.Utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    @Autowired
    private PointsService pointsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "points.routingkey";


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
        int userId = userService.getUserByEmail(userDTO.getEmail()).getUserId();
        pointsService.initiateScore(userId);
        return Result.success();
    }

    @Operation(summary = "登录功能")
    @PostMapping("/login")
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

    @Operation(summary = "更新用户")
    @PutMapping("/updateUser")
    public Result updateUser(@RequestParam int userId,
                             @RequestParam(required = false) String username,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String gender,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) MultipartFile avatorPic) throws IOException {

        // 使用 userId 查找现有用户
        User existingUser = userService.getUser(userId);
        if (existingUser == null) {
            return Result.error("User does not exist");
        }

        // 使用提供的值更新用户信息，如果未提供则保留原始值
        byte[] avator = avatorPic != null ? avatorPic.getBytes() : existingUser.getAvator();
        String updatedUsername = username != null ? username : existingUser.getUsername();
        String updatedPassword = password != null ? password : existingUser.getPassword();
        String updatedEmail = email != null ? email : existingUser.getEmail();
        String updatedGender = gender != null ? gender : existingUser.getGender();
        String updatedStatus = status != null ? status : existingUser.getStatus();

        // 调用服务层更新用户信息
        userService.updateUser(userId, updatedUsername, updatedPassword, updatedEmail, updatedGender, updatedStatus, avator);
        return Result.success("User updated successfully");
    }
    
    @Operation(summary = "删除用户")
    @DeleteMapping("/deleteUser/{id}")
    public Result deleteUser(@PathVariable int id) {
        User user = userService.getUser(id);
        if (user == null) {
            return Result.error("User not found");
        }

        userService.deleteUser(id);
        return Result.success("User deleted successfully");
    }

    @Operation(summary = "查询全部用户")
    @GetMapping("/getAll")
    public Result getAll() {
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }

    @Operation(summary = "查询好友关系")
    @GetMapping("/getFriends")
    public Result getFriends(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        List<User>userList = userService.getAllFriends(userId);
        return Result.success(userList);
    }





//    @PostMapping("/changePassword")
//    public Result changePassword(@RequestParam int Email,
//                                 @RequestParam String oldPassword,
//                                 @RequestParam String newPassword){
//
//    }

}
