package com.idaiq.zookeeperAdmin.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ClusterDataList {
    private String path;
    private List<String> list;
    private String[] paths;
}
