package com.zh.module.exception;

/**
 * Created by Administrator on 2018/7/12 0012.
 * 请先登录
 */
public class NoTokenException extends RuntimeException {

    public NoTokenException(String message){
        super(message);
    }

}
