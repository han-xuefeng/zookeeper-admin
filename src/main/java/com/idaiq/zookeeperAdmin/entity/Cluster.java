package com.idaiq.zookeeperAdmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table
@Data
public class Cluster {
    @Column(length = 36)
    @Id
    private String id;

    @Column(length = 200)
    private String name;

    @Column(length = 500)
    private String servers;

    @Column(length = 500)
    private String acl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created;
}
