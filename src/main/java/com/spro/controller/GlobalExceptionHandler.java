package com.spro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)//指定拦截的异常
    public Map<String,Object> errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
        Map<String,Object> modelMap = new HashMap<String,Object>();
        logger.info("message" + e.getMessage());
        modelMap.put("data",null);
        modelMap.put("success",false);
        modelMap.put("message",e.getMessage());
        return modelMap;
    }
}
