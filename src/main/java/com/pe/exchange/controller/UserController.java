package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.config.SmsConfig;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.service.UserPayInfoService;
import com.pe.exchange.service.UserPayPwdService;
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
    @Autowired
    UserPayInfoService userPayInfoService;

    @Autowired
    UserPayPwdService userPayPwdService;
    @SuppressWarnings("rawtypes")


    /***
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("login")
    @ApiOperation("登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile",value = "用户名/手机号/UID",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "password",value = "密码",dataType = "String",  paramType = "query", required = true)})
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
    @ApiOperation("注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "code",value = "验证码",dataType = "String", paramType = "query", required = true) })
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
    @ApiOperation("校验验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile",value = "手机号码",dataType = "String", paramType = "query" , required = true),
    	@ApiImplicitParam(name = "code",value = "验证码",dataType = "String", paramType = "query", required = true)})
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

    @ApiOperation("验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "areaCode",value = "归属地",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "mobile",value = "手机号",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "type",value = "类型",dataType = "int",  paramType = "query", required = true)})
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
    @ApiOperation("修改用户资料")
    public Result updateUserInfo(UserInfo userInfo) {
    	userService.updateUserInfo(userInfo);
    	return Results.success();
    }
    
    /***
     * 查询用户绑定支付方式类型
     * @return
     */
    @PostMapping("findUserPayInfo")
    @ApiOperation("查询用户绑定支付方式类型")
    public Result findUserPayInfo() {
    	return Results.success(userPayInfoService.findUserPayInfoList());
    }
    
    /***
     * 绑定支付方式
     * @param payInfo
     * @return
     */
    @PostMapping("saveUserPayInfo")
    @ApiOperation("绑定用户支付方式")
    public Result saveUserPayInfo(UserPayInfo payInfo) {
    	userPayInfoService.saveUserPayInfo(payInfo);
    	return Results.success();
    }
    
    
    /***
     * 查询用户信息
     * @param userName
     * @return
     */
    @PostMapping("findUserBy")
    public Result findUserBy(@RequestParam("userName") String userName) {
    	return Results.success(userService.findUserBy(userName));
    }
    
    /***
     * 找回密码
     * @param userName
     * @return
     */
    @PostMapping("updateUser")
    public Result updateUserInfo(@RequestParam("userName") String userName,@RequestParam("code")String code,@RequestParam("pwd")String pwd) {
    	userService.updateUserInfo(userName,code,pwd);
    	return Results.success();
    }
    
    /***
     * 用户名是否存在
     * @param userName
     * @return
     */
    @PostMapping("userNameExists")
    public Result userNameExists(@RequestParam("userName") String userName) {
    	userService.userNameExists(userName);
    	return Results.success();
    }
    
    
    @PostMapping("userPayPwdExists")
    public Result userPayPwdExits() {
    	
    	return Results.success(userPayPwdService.isExits());
    }
    
    @PostMapping("userPayPwdIsOk")
    public Result userPayPwdIsOk(@RequestParam("payPwd") String payPwd) {
    	return Results.success(userPayPwdService.isExits(payPwd));
    }
    
    @PostMapping("setPayPwd")
    public Result setPayPwd(@RequestParam("payPwd") String payPwd) {
    	userPayPwdService.updatePayPwd(payPwd);
    	return Results.success();
    }
    
}
