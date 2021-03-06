package com.pe.exchange.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.config.SmsConfig;
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.entity.UserParamCode;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.service.UserPayInfoService;
import com.pe.exchange.service.UserPayPwdService;
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
    public Result doLogin(@RequestBody Map<String,String> params) {
                String login = userService.login(params.get("mobile"),params.get("password"));
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
    public Result register(@RequestBody UserParamCode params) {
    	userService.register(params.getUser(),params.getCode());
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
    public Result checkCode(@RequestBody Map<String,String> params) {
    	return Results.success(userService.checkVeriCode(params.get("mobile"), params.get("code")));
    }
    
    /***
     * 发送验证码

     * @return
     */

    @ApiOperation("发送验证码")
	@PostMapping("sendCode")
    public Result sendCode(@RequestBody Map<String,String> params) {
    	userService.getVeriCode(params.get("areaCode"), params.get("mobile"), Integer.valueOf(params.get("type")));
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
    public Result updateUserInfo(@RequestBody UserInfo userInfo) {
    	userService.updateUserInfo(userInfo);
    	return Results.success();
    }
    
    /***
     * 查询用户个人资料
     * @param userInfo
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("findUserInfo")
    @ApiOperation("查询用户资料")
    public Result<UserInfo> findUserInfo() {
    	return Results.success(userService.findUserInfo());
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
    public Result saveUserPayInfo(@RequestBody UserPayInfo payInfo) {
    	userPayInfoService.saveUserPayInfo(payInfo);
    	return Results.success();
    }
    
    
    /***
     * 查询用户信息
     * @param userName
     * @return
     */
    @ApiOperation("查询用户信息")
    @PostMapping("findUserBy")
    public Result findUserBy() {
    	return Results.success(userService.findUserBy());
    }
    
    /***
     * 找回密码
     * @param userName
     * @return
     */
    @ApiOperation("找回密码")
    @PostMapping("getPwdUser")
    public Result updateUserInfo(@RequestBody Map<String,String> params) {
    	userService.getUserPwd(params.get("userName"),params.get("telephone"),params.get("code"),params.get("pwd"));
    	return Results.success();
    }
    
    
    /***
     * 重置用户登录
     * @param params
     * @return
     */
    @ApiOperation("重置用户登录")
    @PostMapping("setNewPwd")
    public Result setNewPwd(@RequestBody Map<String,String> params) {
    	userService.updateUserPwd(params.get("pwd"), params.get("newPwd"));
    	return Results.success();
    }
    
    /***
     * 用户名是否存在
     * @param userName
     * @return
     */
    @ApiOperation("用户名是否存在")
    @PostMapping("userNameExists")
    public Result userNameExists(@RequestBody Map<String, String> params) {
    	userService.userNameExists(params.get("userName"));
    	return Results.success();
    }
    
    /***
     * 用户是否绑定支付密码
     * @return
     */
    @ApiOperation("用户是否绑定支付密码")
    @PostMapping("userPayPwdExists")
    public Result userPayPwdExits() {
    	return Results.success(userPayPwdService.isExits());
    }
    
    /***
     * 用户支付密码验证是否通过
     * @param params
     * @return
     */
    @ApiOperation("用户支付密码验证是否通过")
    @PostMapping("userPayPwdIsOk")
    public Result userPayPwdIsOk(@RequestBody  Map<String, String> params) {
    	return Results.success(userPayPwdService.isExits(params.get("payPwd")));
    }
    
    /***
     * 设置用户支付密码
     * @param params
     * @return
     */
    @ApiOperation("设置用户支付密码")
    @PostMapping("setPayPwd")
    public Result setPayPwd(@RequestBody Map<String, String> params) {
    	userPayPwdService.updatePayPwd(params.get("payPwd"),params.get("code"));
    	return Results.success();
    }
    
    /***
     * 当前用户版本，是否更新版本
     * @param params
     * @return
     */
    @ApiOperation("当前用户版本，是否更新版本")
    @PostMapping("newVersion")
    public Result findNewVersion() {
    	return Results.success(userService.getNewVersionInfo());
    }
    
    
    /***
     * 我的团队
     * @param params
     * @return
     */
    @ApiOperation("我的团队")
    @PostMapping("myTeam")
    public Result findMyTeam() {
    	return Results.success(userService.findMyTeam());
    }
}
