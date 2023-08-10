package com.idaiq.zookeeperAdmin.controller.vo;

import lombok.Data;
import org.apache.zookeeper.data.Stat;

@Data
public class ClusterDataItem {
    String path;

    String data;

    Stat stat;
}
