package com.example.demo.controller;


import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("向用户：{}发送链接",user);
        return userService.generateLinks(user);
    }
    @GetMapping("/activation")
    public Result activationSAccount(String confirmCode){
        log.info("激活用户的验证码：{}",confirmCode);
        return userService.activationAccount(confirmCode);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("登录：{}",user);
        return userService.login(user);
    }

    @GetMapping("/me")
    public Result findUserInf(){
        log.info("查询用户信息");
        return userService.userInf();

    }


}
