package com.pe.exchange.exception;

import com.pe.exchange.common.ResultEnum;
import lombok.Data;

@Data
public class SysException extends BaseException {
    private static int  code= ResultEnum.INTERNAL_SERVER_ERROR.getCode();
    private static String msg="服务器内部错误";

    public SysException(){
        super(code,msg);
    }
    public SysException(int code,String msg){
        super(code,msg);

    }
    public SysException(ResultEnum resultEnum){
        super(resultEnum);

    }
}
