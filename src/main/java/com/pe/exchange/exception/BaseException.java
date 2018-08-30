package com.pe.exchange.exception;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.common.Results;
import lombok.Data;

@Data
public abstract class BaseException extends RuntimeException {
    private Result result;

    public BaseException(int code,String msg){
        super(msg);
        this.result=new Result().setCode(code).setMessage(msg);

    }
    public BaseException(ResultEnum resultEnum){
        this.result= Results.fail(resultEnum);

    }
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
    
    
}
