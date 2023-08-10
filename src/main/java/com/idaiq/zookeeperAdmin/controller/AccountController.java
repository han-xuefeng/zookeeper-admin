package com.idaiq.zookeeperAdmin.controller;

import com.idaiq.zookeeperAdmin.controller.dto.LoginAccount;
import com.idaiq.zookeeperAdmin.controller.dto.Result;
import com.idaiq.zookeeperAdmin.service.UserService;
import com.idaiq.zookeeperAdmin.util.Web;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AccountController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginAccount loginAccount){
        String token = userService.login(loginAccount);
        return Result.success(Map.of(
                "token", token
        ));
    }

    @PostMapping("/logout")
    public Result logout() {
        String token = Web.getToken();
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/info")
    public Result info() {
        String token = Web.getToken();
        return Result.success(userService.info(token));
    }
}
