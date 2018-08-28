package com.pe.exchange.exception;

import com.pe.exchange.common.ResultEnum;
import lombok.Data;


public class BizException extends BaseException {

    public BizException(int code,String msg){
        super(code,msg);

    }
    public BizException(ResultEnum resultEnum){
        super(resultEnum);

    }

}
