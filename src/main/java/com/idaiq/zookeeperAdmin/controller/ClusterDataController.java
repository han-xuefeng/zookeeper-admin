package com.idaiq.zookeeperAdmin.controller;

import com.idaiq.zookeeperAdmin.controller.dto.Result;
import com.idaiq.zookeeperAdmin.controller.vo.ClusterDataItem;
import com.idaiq.zookeeperAdmin.controller.vo.ClusterDataList;
import com.idaiq.zookeeperAdmin.service.ClusterDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/clusterData")
@RestController
public class ClusterDataController {
    @Resource
    private ClusterDataService clusterDataService;

    @GetMapping("/list")
    public Result list(
            @RequestParam String id,
            @RequestParam(defaultValue = "/") String path
    ) {
        ClusterDataList data = null;
        try {
            data = clusterDataService.getDataList(id, path);
            if (!path.equals("/")) {
                data.setPaths(path.substring(1).split("/"));
            } else {
                data.setPaths(new String[]{});
            }
            return Result.success(data);
        }catch (RuntimeException e){
            return Result.failure("节点连接异常");
        } catch (Exception e) {
            return Result.error("系统异常");
        }
    }


    @GetMapping("/item")
    public Result item(
            @RequestParam String id,
            @RequestParam(defaultValue = "/") String path
    ) {
        try {
            ClusterDataItem dataItem = clusterDataService.getDataItem(id, path);
            return Result.success(dataItem);
        } catch (RuntimeException e){
            return Result.failure("节点连接异常");
        } catch (Exception e) {
            return Result.error("系统异常");
        }
    }
}
