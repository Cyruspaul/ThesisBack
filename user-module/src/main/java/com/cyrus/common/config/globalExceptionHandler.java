package com.cyrus.common.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

//统一异常处理器（1.全局异常处理 2.特定异常处理 3.自定义异常处理）
@ControllerAdvice
@Slf4j//讲异常写入日志文件
public class globalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody//返回数据
    public APIResponse error(Exception e) {
        e.printStackTrace();
        return APIResponse.warning().message("执行全局异常处理");
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseBody//返回数据
    public APIResponse error(HttpClientErrorException.NotFound e) {
        e.printStackTrace();
        return APIResponse.warning().message("执行全局异常处理");
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseBody//返回数据
    public APIResponse error(ChangeSetPersister.NotFoundException e) {
        e.printStackTrace();
        return APIResponse.warning().message("执行全局异常处理");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//返回数据
    public APIResponse error(ArithmeticException e) {
        e.printStackTrace();
        return APIResponse.warning().message("算数异常ArithmeticException");
    }

    //自定义异常
    @ExceptionHandler(FaSystemException.class)
    @ResponseBody//返回数据
    public APIResponse error(FaSystemException e) {
        e.printStackTrace();
//        log.error(e.getMessage());
        return APIResponse.warning().code(e.getCode()).message("自定义异常 : " + e.getMsg());
    }
}
