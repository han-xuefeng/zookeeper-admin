package com.idaiq.zookeeperAdmin.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> items;
    private long total;

    public static <T> PageResult<T> of(long total, List<T> items) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setItems(items);
        pageResult.setTotal(total);
        return pageResult;
    }
}
