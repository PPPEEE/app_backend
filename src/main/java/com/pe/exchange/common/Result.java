package com.pe.exchange.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class Result<T> {
    @ApiModelProperty(position = 0)
    private int code;
    @ApiModelProperty(position = 1)
    private String message;
    @ApiModelProperty(position = 2)
    private  T data;

    public int getCode() {
        return code;
    }

    public Result() {
    }
    public Result(ResultEnum resultEnum) {
        this.code=resultEnum.getCode();
        this.message=resultEnum.getMsg();
    }

    public Result setCode(int resultCode) {
        this.code = resultCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }
}
