package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;

import com.pe.exchange.config.SmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhaodong
 * @version v1.0
 * @email zhaodongxx@outlook.com
 * @since 2018/3/30 23:05
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired SmsConfig smsConfig;
    @GetMapping("login")
    public Result doLogin() {

        System.out.println(smsConfig.getLocal().getUrl());
        return Results.success("登录成功");
    }
}
