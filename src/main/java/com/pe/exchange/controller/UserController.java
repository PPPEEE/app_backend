package com.pe.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.common.Results;
import com.pe.exchange.config.SmsConfig;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.service.UserPayInfoService;
import com.pe.exchange.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
    @Autowired
    UserPayInfoService userPayInfoService;

    @SuppressWarnings("rawtypes")
	@ApiOperation("登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile",value = "用户名/手机号/UID",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "password",value = "密码",dataType = "String",  paramType = "query", required = true)})

    /***
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("login")
    public Result doLogin(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {
            String login = userService.login(mobile, password);
            return Results.success(login);
    }
    
    /***
     * 注册
     * @param user
     * @param code
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("register")
    public Result register(User user,@RequestParam("code") String code) {
    	userService.register(user,code);
    	return Results.success();
    } 
    
    /***
     * 校验验证码
     * @param mobile
     * @param code
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("checkCode")
    public Result checkCode(@RequestParam("mobile") String mobile,@RequestParam("code") String code) {
    	return Results.success(userService.checkVeriCode(mobile, code));
    }
    
    /***
     * 发送验证码
     * @param areaCode
     * @param mobile
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("sendCode")
    public Result sendCode(@RequestParam("areaCode") String areaCode,@RequestParam("mobile") String mobile,@RequestParam("type") int type) {
    	userService.getVeriCode(areaCode, mobile, type);
    	return Results.success();
    }
    
    /***
     * 修改用户个人资料
     * @param userInfo
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("updateUserInfo")
    public Result updateUserInfo(UserInfo userInfo) {
    	userService.updateUserInfo(userInfo);
    	return Results.success();
    }
    
    /***
     * 查询用户绑定支付方式类型
     * @return
     */
    @PostMapping("findUserPayInfo")
    public Result findUserPayInfo(@RequestParam("token") String token) {
    	return Results.success(userPayInfoService.findUserPayInfoList(token));
    }
    
    @PostMapping("saveUserPayInfo")
    public Result saveUserPayInfo(UserPayInfo payInfo) {
    	userPayInfoService.saveUserPayInfo(payInfo);
    	return Results.success();
    }
}
