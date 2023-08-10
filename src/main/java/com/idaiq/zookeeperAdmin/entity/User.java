package com.idaiq.zookeeperAdmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class User {

    @Column(length = 36)
    @Id
    private String id;
    @Column(length = 100, nullable = false, unique = true)
    private String username;
    @Column(length = 300)
    private String password;
}
