package com.idaiq.zookeeperAdmin.controller.handle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Map<String, Object> illegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();
        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        return data;
    }
}
