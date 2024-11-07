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
import java.util.HashMap;
import java.util.List;
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

    @Operation(summary = "修改用户信息")
    @PutMapping("/updateUser")
    public Result updateUser(@ModelAttribute @Validated UserDTO userDTO,
                             @RequestParam(required = false) MultipartFile avatorPic) throws IOException {
        User existingUser = userService.getUserByEmail(userDTO.getEmail());
        if (existingUser == null) {
            return Result.error("User does not exist");
        }

        byte[] avator = null;
        if (avatorPic != null) {
            avator = avatorPic.getBytes();
        }

        // 使用已存在的 userId 更新用户信息
        userService.updateUser(existingUser.getUserId(), userDTO.getUsername(), userDTO.getEmail(),
                userDTO.getGender(), userDTO.getStatus(), avator);
        return Result.success("User updated successfully");
    }

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
