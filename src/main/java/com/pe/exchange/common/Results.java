package com.pe.exchange.common;

public class Results {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";


    public static Result success() {
        return new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> success(T data) {
        return new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMsg())
                .setData(data);
    }

    public static Result fail(String message) {
        return new Result()
                .setCode(ResultEnum.FAIL.getCode())
                .setMessage(message);
    }

    public static Result unauthorized() {
        return new Result()
                .setCode(ResultEnum.UNAUTHORIZED.getCode())
                .setMessage(ResultEnum.UNAUTHORIZED.getMsg());
    }
}
