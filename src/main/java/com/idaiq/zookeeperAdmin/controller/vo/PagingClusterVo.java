package com.idaiq.zookeeperAdmin.controller.vo;

import com.idaiq.zookeeperAdmin.entity.Cluster;
import lombok.Data;

@Data
public class PagingClusterVo {

    private String id;

    private String name;

    private Boolean status;

    private String servers;


    public void clusterToVoCluster(Cluster cluster) {
        id = cluster.getId();
        name = cluster.getName();
//        status = false;
        servers = cluster.getServers();
    }

}
