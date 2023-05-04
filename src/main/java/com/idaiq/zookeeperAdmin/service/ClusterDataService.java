package com.idaiq.zookeeperAdmin.service;

import com.idaiq.zookeeperAdmin.controller.form.CreateNodeForm;
import com.idaiq.zookeeperAdmin.controller.vo.ClusterDataItem;
import com.idaiq.zookeeperAdmin.controller.vo.ClusterDataList;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterDataService {

    @Autowired
    @Lazy
    private ZkClientService zkClientService;

    public ClusterDataList getDataList(String id, String path) throws Exception {
        CuratorFramework curatorFramework = checkConnect(id);
        if (curatorFramework == null) {
            throw new RuntimeException("error");
        }

        List<String> strings = curatorFramework.getChildren().forPath(path);
        ClusterDataList clusterDataList = new ClusterDataList();
        clusterDataList.setPath(path);
        clusterDataList.setList(strings);
        return clusterDataList;
    }

    public CuratorFramework checkConnect(String id) {
        CuratorFramework zkClient = zkClientService.getZkClient(id);
        if (zkClient == null) {
            return null;
        }
        if (!zkClient.getZookeeperClient().isConnected()) {
            zkClientService.removeZkClient(id);
            return null;
        }
        return zkClient;
    }


    public ClusterDataItem getDataItem(String id, String path) throws Exception {
        CuratorFramework curatorFramework = checkConnect(id);
        if (curatorFramework == null) {
            throw new RuntimeException("error");
        }

        byte[] bytes = curatorFramework.getData().forPath(path);

        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath(path);

        ClusterDataItem clusterDataItem = new ClusterDataItem();
        if (bytes != null) {
            clusterDataItem.setData(new String(bytes));
        } else {
            clusterDataItem.setData("");
        }
        clusterDataItem.setPath(path);
        clusterDataItem.setStat(stat);
        return clusterDataItem;
    }

    public void createNode(String id, CreateNodeForm createNodeForm) throws Exception {
        CuratorFramework curatorFramework = checkConnect(id);
        if (curatorFramework == null) {
            throw new RuntimeException("error");
        }
        curatorFramework.create().forPath(createNodeForm.getParentPath() + "/" + createNodeForm.getPath(), createNodeForm.getData().getBytes());
    }
}
