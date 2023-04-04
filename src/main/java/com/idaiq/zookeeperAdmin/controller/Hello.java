package com.idaiq.zookeeperAdmin.controller;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Hello {

    @Resource
    private WebProperties webProperties;

    @GetMapping("/hello")
    public String world() throws IOException {
        System.out.println(webProperties.getResources().getStaticLocations().length);

        return "this is a test";
    }
}
