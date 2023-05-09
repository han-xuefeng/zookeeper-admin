package com.idaiq.zookeeperAdmin.controller;

import com.idaiq.zookeeperAdmin.controller.dto.Result;
import com.idaiq.zookeeperAdmin.controller.form.CreateNodeForm;
import com.idaiq.zookeeperAdmin.controller.form.DeleteNodeForm;
import com.idaiq.zookeeperAdmin.controller.vo.ClusterDataItem;
import com.idaiq.zookeeperAdmin.controller.vo.ClusterDataList;
import com.idaiq.zookeeperAdmin.service.ClusterDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("create")
    public Result create(
            @RequestParam String id,
            @RequestBody CreateNodeForm createNodeForm
            ){

        try {

            if (createNodeForm.getParentPath() == null || "/".equals(createNodeForm.getParentPath())) {
                createNodeForm.setParentPath("");
            }

            clusterDataService.createNode(id, createNodeForm);
            return Result.success("success");
        } catch (RuntimeException e){
//            throw e;
            return Result.failure("节点创建失败");
        } catch (Exception e) {
            return Result.error("创建节点失败");
        }
    }

    @PostMapping("delete")
    public Result delete(
            @RequestParam String id,
            @RequestBody DeleteNodeForm deleteNodeForm
            ) throws Exception {

        try {

            clusterDataService.deleteNode(id, deleteNodeForm.getPath());
            return Result.success("success");
        } catch (RuntimeException e){
//            throw e;
            return Result.failure("节点删除失败");
        } catch (Exception e) {
            return Result.error("删除节点失败"+e.getMessage());
        }
    }
}
