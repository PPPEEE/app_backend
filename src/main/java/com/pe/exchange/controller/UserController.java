package com.pe.exchange.controller;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
=======
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
import org.springframework.web.bind.annotation.RestController;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.common.Results;
import com.pe.exchange.config.SmsConfig;
<<<<<<< HEAD
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.service.UserPayInfoService;
=======
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.entity.UserParamCode;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.service.UserPayInfoService;
import com.pe.exchange.service.UserPayPwdService;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
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
<<<<<<< HEAD

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
=======

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
    public Result doLogin(@RequestBody Map<String,String> params) {
            String login = userService.login(params.get("mobile"),params.get("password"));
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
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
<<<<<<< HEAD
    public Result register(User user,@RequestParam("code") String code) {
    	userService.register(user,code);
=======
    @ApiOperation("注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "code",value = "验证码",dataType = "String", paramType = "query", required = true) })
    public Result register(@RequestBody UserParamCode params) {
    	userService.register(params.getUser(),params.getCode());
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
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
<<<<<<< HEAD
    public Result checkCode(@RequestParam("mobile") String mobile,@RequestParam("code") String code) {
    	return Results.success(userService.checkVeriCode(mobile, code));
=======
    @ApiOperation("校验验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile",value = "手机号码",dataType = "String", paramType = "query" , required = true),
    	@ApiImplicitParam(name = "code",value = "验证码",dataType = "String", paramType = "query", required = true)})
    public Result checkCode(@RequestBody Map<String,String> params) {
    	return Results.success(userService.checkVeriCode(params.get("mobile"), params.get("code")));
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
    }
    
    /***
     * 发送验证码
     * @param areaCode
     * @param mobile
     * @param type
     * @return
     */
<<<<<<< HEAD
    @SuppressWarnings("rawtypes")
	@PostMapping("sendCode")
    public Result sendCode(@RequestParam("areaCode") String areaCode,@RequestParam("mobile") String mobile,@RequestParam("type") int type) {
    	userService.getVeriCode(areaCode, mobile, type);
=======

    @ApiOperation("验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "areaCode",value = "归属地",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "mobile",value = "手机号",dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "type",value = "类型",dataType = "int",  paramType = "query", required = true)})
	@PostMapping("sendCode")
    public Result sendCode(@RequestBody Map<String,String> params) {
    	userService.getVeriCode(params.get("areaCode"), params.get("mobile"), Integer.valueOf(params.get("type")));
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
    	return Results.success();
    }
    
    /***
     * 修改用户个人资料
     * @param userInfo
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("updateUserInfo")
<<<<<<< HEAD
    public Result updateUserInfo(UserInfo userInfo) {
=======
    @ApiOperation("修改用户资料")
    public Result updateUserInfo(@RequestBody UserInfo userInfo) {
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
    	userService.updateUserInfo(userInfo);
    	return Results.success();
    }
    
    /***
     * 查询用户绑定支付方式类型
     * @return
     */
    @PostMapping("findUserPayInfo")
<<<<<<< HEAD
    public Result findUserPayInfo(@RequestParam("token") String token) {
    	return Results.success(userPayInfoService.findUserPayInfoList(token));
    }
    
    @PostMapping("saveUserPayInfo")
    public Result saveUserPayInfo(UserPayInfo payInfo) {
    	userPayInfoService.saveUserPayInfo(payInfo);
    	return Results.success();
=======
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
    public Result saveUserPayInfo(@RequestBody UserPayInfo payInfo) {
    	userPayInfoService.saveUserPayInfo(payInfo);
    	return Results.success();
    }
    
    
    /***
     * 查询用户信息
     * @param userName
     * @return
     */
    @PostMapping("findUserBy")
    public Result findUserBy() {
    	return Results.success(userService.findUserBy());
    }
    
    /***
     * 找回密码
     * @param userName
     * @return
     */
    @PostMapping("updateUser")
    public Result updateUserInfo(@RequestBody Map<String,String> params) {
    	userService.updateUserInfo(params.get("userName"),params.get("telepone"),params.get("code"),params.get("pwd"));
    	return Results.success();
    }
    
    /***
     * 用户名是否存在
     * @param userName
     * @return
     */
    @PostMapping("userNameExists")
    public Result userNameExists(@RequestBody Map<String, String> params) {
    	userService.userNameExists(params.get("userName"));
    	return Results.success();
    }
    
    
    @PostMapping("userPayPwdExists")
    public Result userPayPwdExits() {
    	return Results.success(userPayPwdService.isExits());
    }
    
    @PostMapping("userPayPwdIsOk")
    public Result userPayPwdIsOk(@RequestBody  Map<String, String> params) {
    	return Results.success(userPayPwdService.isExits(params.get("payPwd")));
    }
    
    @PostMapping("setPayPwd")
    public Result setPayPwd(@RequestBody Map<String, String> params) {
    	userPayPwdService.updatePayPwd(params.get("payPwd"));
    	return Results.success();
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
    }
    
}
