package com.idaiq.zookeeperAdmin;

import com.idaiq.zookeeperAdmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@SpringBootApplication
public class ZookeeperAdminApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        initDatabaseDir();
        SpringApplication.run(ZookeeperAdminApplication.class, args);
    }

    public static void initDatabaseDir() throws IOException {
        Path dbPath = Paths.get("data");
        if (!Files.exists(dbPath)) {
            Files.createDirectory(dbPath);
            log.debug("create dir: {}", dbPath);
        }
    }

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.initUser();
    }
}
