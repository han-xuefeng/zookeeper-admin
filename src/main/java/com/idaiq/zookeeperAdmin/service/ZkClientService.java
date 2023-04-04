package com.idaiq.zookeeperAdmin.service;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@SessionScope
public class ZkClientService {
    private final ConcurrentHashMap<String, CuratorFramework> clients = new ConcurrentHashMap<>();

    public void addZkClient(String id, CuratorFramework curatorFramework){
        clients.put(id, curatorFramework);
        System.out.println(clients.size());
    }

    public CuratorFramework getZkClient(String id) {
        return clients.get(id);
    }

    public Map<String, CuratorFramework> getClients()
    {
        return clients;
    }

    public void removeZkClient(String id){
        clients.remove(id);
    }
}
