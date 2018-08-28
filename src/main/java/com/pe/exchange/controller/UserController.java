package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.config.SmsConfig;
import com.pe.exchange.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhaodong
 * @version v1.0
 * @email zhaodongxx@outlook.com
 * @since 2018/3/30 23:05
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    SmsConfig smsConfig;
    @Autowired
    UserService userService;

    @ApiOperation("登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile",value = "用户名/手机号/UID",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "password",value = "密码",dataType = "String",  paramType = "query", required = true)})

    @PostMapping("login")
    public Result doLogin(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {

            String login = userService.login(mobile, password);
            return Results.success(login);

    }
}
