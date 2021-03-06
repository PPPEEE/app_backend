package com.pe.exchange.common;

public enum ResultEnum {
    SUCCESS(200,"成功"),//成功
    FAIL(400,"失败"),//失败
    UNAUTHORIZED(401,"未认证"),//未认证（签名错误）
    LOGIN_FAIL(402,"用户名或密码错误"),//未认证（签名错误）
    USER_ALREADY_EXISTS(301,"用户已存在"),
    TELPHONE_ALREADY_EXISTS(307,"该手机已被注册"),
    CODE_ERROR(302,"验证码失效"),
    USER_FAIL(303,"用户失效！"),
    USER_NOT_EXISTS(304,"目标用户不存在"),
    PAY_PWD_ERROR(305,"支付密码错误"),
    NOT_FOUND(404,"接口不存在"),//接口不存在
    INTERNAL_SERVER_ERROR(500,"内部错误"),

    INSUFFICIENT_BALANCE(601,"余额不足");

    private int code;
    private String msg;

    ResultEnum(int code,String msg) {
        this.code = code;
        this.msg=msg;
    }

    public int getCode() {
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
