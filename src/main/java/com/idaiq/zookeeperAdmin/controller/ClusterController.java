package com.idaiq.zookeeperAdmin.controller;

import com.idaiq.zookeeperAdmin.controller.dto.PageResult;
import com.idaiq.zookeeperAdmin.controller.dto.Result;
import com.idaiq.zookeeperAdmin.controller.vo.PagingClusterVo;
import com.idaiq.zookeeperAdmin.entity.Cluster;
import com.idaiq.zookeeperAdmin.service.ClusterService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/clusters")
@RestController
public class ClusterController {

    @Resource
    private ClusterService clusterService;

    @GetMapping("/paging")
    public Result page(
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize,
            String name
    ){

        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "created");

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Cluster query = new Cluster();
        query.setName(name);

        Example<Cluster> example = Example.of(query, exampleMatcher);

        Page<Cluster> page = clusterService.findAll(example, pageRequest);

        List<PagingClusterVo> items = clusterService.refreshList(page.getContent());

        return Result.success(PageResult.of(page.getTotalElements(), items));
    }

    @PostMapping("")
    public Result create(@RequestBody Cluster cluster) throws ExecutionException, InterruptedException {
        if(cluster.getServers() == null || cluster.getServers().equals("")) {
            throw new  IllegalArgumentException("server is empty");
        }
        clusterService.create(cluster);
        return Result.success();
    }

    @GetMapping ("connect")
    public Result connect(
            @RequestParam String id
    ) {

        try {
            if(id.equals("")) {
                return Result.failure("server is empty");
            }
            clusterService.connect(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure("连接失败");
        }
    }
}
