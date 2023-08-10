package com.idaiq.zookeeperAdmin.util;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zookeeper 的工具 maven包上的sdk
 */
public class ZkUtil {
    private static final int TIMEOUT = 40000;
    public static CuratorFramework connect(String server) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(server)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(TIMEOUT)
                .connectionTimeoutMs(TIMEOUT)
                .build();
        client.start();
        System.out.println(client.getZookeeperClient().isConnected());
        System.out.println(client.getState().name());
        boolean isZkCuratorStarted = client.getState() == CuratorFrameworkState.STARTED;
        System.out.println("当前客户的状态：" + (isZkCuratorStarted ? "连接中" : "已关闭"));
        return client;
    }

}
