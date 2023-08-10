package com.idaiq.zookeeperAdmin.controller.dto;

import lombok.Data;

@Data
public class LoginAccount {
    private String username;
    private String password;
    private Boolean remember;
}

