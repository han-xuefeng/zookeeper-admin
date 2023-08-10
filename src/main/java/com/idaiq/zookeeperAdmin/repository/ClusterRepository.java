package com.idaiq.zookeeperAdmin.repository;

import com.idaiq.zookeeperAdmin.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, String> {

}
