package com.damian.blog;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//用@@ResponseStatus(HttpStatus.NOT_FOUND)指定一个返回的状态
@ResponseStatus(HttpStatus.NOT_FOUND)
//新建一个错误类型
public class NotFoundException  extends RuntimeException{
   public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException() {
        super();
    }
}
