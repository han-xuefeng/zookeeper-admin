package com.idaiq.zookeeperAdmin.controller.dto;

import lombok.Data;

@Data
public class Result {

    private Object data;

    private Integer code;

    private String message;

    public static Result success(Object data) {
        Result result = new Result();

        result.setCode(0);
        result.setData(data);
        result.setMessage("success");

        return result;
    }

    public static Result success() {
        Result result = new Result();

        result.setCode(0);
        result.setMessage("success");

        return result;
    }

    public static Result success(String message) {
        Result result = new Result();

        result.setCode(0);
        result.setMessage(message);

        return result;
    }

    public static Result success(String message, Object data) {
        Result result = new Result();

        result.setCode(0);
        result.setMessage(message);
        result.setData(data);

        return result;
    }


    public static Result failure(String message){
        Result result = new Result();

        result.setCode(-1);
        result.setMessage(message);
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();

        result.setCode(999);
        result.setMessage(message);

        return result;
    }
}
