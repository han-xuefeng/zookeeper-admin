package com.idaiq.zookeeperAdmin.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.idaiq.zookeeperAdmin.controller.dto.LoginAccount;
import com.idaiq.zookeeperAdmin.entity.User;
import com.idaiq.zookeeperAdmin.repository.UserRepository;
import com.idaiq.zookeeperAdmin.util.ID;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Value("${default.username}")
    private String username;
    @Value("${default.password}")
    private String password;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Resource
    private UserRepository userRepository;

    @Resource
    private Cache<String, User> tokenManager;

    public void initUser(){
        if (CollectionUtils.isEmpty(userRepository.findAll())) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(username);
            String encodePassword = this.passwordEncoder.encode(password);
            user.setPassword(encodePassword);
            userRepository.saveAndFlush(user);
            log.info("初始用户名和密码为: {}/{}", username, password);
        }
    }


    public String login(LoginAccount loginAccount){
        User user = userRepository.findByUsername(loginAccount.getUsername())
                .orElseThrow(()->new IllegalArgumentException("用户名或密码错误"));

        boolean matches = passwordEncoder.matches(loginAccount.getPassword(), user.getPassword());
        if (!matches) {
            throw new IllegalArgumentException("用户名或密码错误");
        }

        String token = ID.uuid();
        tokenManager.put(token, user);
        return token;
    }

    public User info(String token) {
        User user = tokenManager.getIfPresent(token);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void logout(String token) {
        tokenManager.invalidate(token);
    }
}
