package com.idaiq.zookeeperAdmin.service;

import com.idaiq.zookeeperAdmin.controller.vo.PagingClusterVo;
import com.idaiq.zookeeperAdmin.entity.Cluster;
import com.idaiq.zookeeperAdmin.repository.ClusterRepository;
import com.idaiq.zookeeperAdmin.util.ID;
import com.idaiq.zookeeperAdmin.util.Networks;
import com.idaiq.zookeeperAdmin.util.ZkUtil;
import jakarta.annotation.Resource;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class ClusterService {

    @Resource
    private ClusterRepository clusterRepository;

    @Autowired
    @Lazy
    private ZkClientService zkClientService;

    public Page<Cluster> findAll(Example<Cluster> example, PageRequest pageRequest) {
        return clusterRepository.findAll(example, pageRequest);
    }

    public List<PagingClusterVo> refreshList(List<Cluster> list){
        List<PagingClusterVo> items = new ArrayList<>();

        for (Cluster cluster : list) {
            PagingClusterVo pagingClusterVo = new PagingClusterVo();
            pagingClusterVo.clusterToVoCluster(cluster);
            pagingClusterVo.setStatus(checkConnect(cluster));
            items.add(pagingClusterVo);
        }
        return items;
    }

    public void create(Cluster cluster) throws ExecutionException, InterruptedException {

        String uuid = ID.uuid();
        for (String server : cluster.getServers().split(",")) {
            String[] split = server.split(":");
            String host = split[0];
            int port = Integer.parseInt(split[1]);
            boolean hostConnected = Networks.isHostConnected(host, port, 30000);
            if (!hostConnected) {
                throw new IllegalArgumentException("server " + server + " can't connected.");
            }
        }

        cluster.setId(uuid);
        cluster.setCreated(new Date());

        clusterRepository.saveAndFlush(cluster);
    }

    public Boolean checkConnect(Cluster cluster){
        CuratorFramework zkClient = zkClientService.getZkClient(cluster.getId());
        if (zkClient == null) {
            return false;
        }
        return zkClient.getZookeeperClient().isConnected();
    }


    public void connect(String id) throws InterruptedException{
        Optional<Cluster> cluster = clusterRepository.findById(id);
        if (cluster.isEmpty()) {
            throw new IllegalArgumentException("Cluster not exist");
        }
        CuratorFramework connect = ZkUtil.connect(cluster.get().getServers());
        if (!connect.blockUntilConnected(3000, TimeUnit.MILLISECONDS)) {
            connect.close();
            throw new IllegalArgumentException("Connection failure");
        }
        zkClientService.addZkClient(id, connect);
    }

    public boolean delete(String id) {
        try {
            clusterRepository.deleteById(id);
            return true;
        }catch (Exception $e) {
            return false;
        }
    }
}
