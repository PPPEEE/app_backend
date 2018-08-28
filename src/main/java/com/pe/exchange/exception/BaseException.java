package com.pe.exchange.exception;

import com.pe.exchange.common.ResultEnum;
import lombok.Data;

@Data
public abstract class BaseException extends RuntimeException {
    private int code;
    private String msg;
    public BaseException(int code,String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }
    public BaseException(ResultEnum resultEnum){
        this.code=resultEnum.getCode();
        this.msg=resultEnum.getMsg();
    }
}
