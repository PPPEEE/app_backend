package com.pe.exchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.config.SmsConfig;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInfoDao;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.exception.BaseException;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.exception.SysException;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.SHA256Util;
import com.pe.exchange.utils.SmsAppUtils;
import com.pe.exchange.utils.TokenGeneratorUtil;
import com.pe.exchange.utils.VeriCodeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final String VERI_CODE_FLAG = "veri";
    private static final String TOKEN_FLAG = "TOKEN";
    @Autowired
    SmsConfig smsConfig;
    @Autowired
    RedisOps redisOps;

    @Autowired
    UserDao userDao;
    
    @Autowired
    UserInfoDao userInfoDao;
    
    
    public void updateUserInfo(UserInfo userInfo) {
    	try {
    		log.info("开始执行修改用户信息,用户ID："+userInfo.getUserId());
    		userInfoDao.save(userInfo);
		} catch (Exception e) {
			log.error("系统异常:",e);
			throw new SysException();
		}
    }
    

    public void getVeriCode(String areaCode, String mobile, int type) {
    	
        // 获取验证码
        String code = VeriCodeUtils.random(6);
        System.out.println(code);
        try {
            // 保存验证码到redis,有效期60s
            String key = mobile + VERI_CODE_FLAG;
            redisOps.setWithTimeout(key, code, 60000);
            // 发送验证码
            sendVeriCode(areaCode, mobile, type, code);
        }catch (BaseException e){
            throw e;
        }
        catch (Exception e) {
            log.error("系统异常",e);
            throw new SysException();
        }


    }

    public void register(User user,String code){
        User u= userDao.findByUserName(user.getUserName());
        if(u!=null){
            throw new BizException(ResultEnum.USER_ALREADY_EXISTS);
        }
        if(!checkVeriCode(user.getTelephone(),code)) {
        	throw new BizException(ResultEnum.CODE_ERROR);
        }
      
        user.setPwd(encryptPwd(user.getPwd()));
        try {
            userDao.save(user);
        } catch (Exception e) {
            log.error("数据插入失败",e);
            throw new SysException();
        }
    }

    public String login(String username,String password){
        User user=userDao.findWithLogin(username);
        if(user==null||!password.equals(user.getPwd()))
        {
            throw new BizException(ResultEnum.LOGIN_FAIL);
        }
        String token= TokenGeneratorUtil.generateValue();
        redisOps.setWithTimeout(token,user.getId().toString(), 1000 * 60 * 30);
        return token;
    }

    public boolean checkVeriCode(String mobile, String code) {
        // 保存验证码到redis,有效期60s
        String key = mobile + VERI_CODE_FLAG;
        String savedCode = redisOps.get(key);
        return code.equals(savedCode);
    }

    private String encryptPwd(String pwd){
         return SHA256Util
             .sha256Str(pwd + SHA256Util.sha256Str(pwd));
    }
    private void sendVeriCode(String areaCode, String mobile, int type, String code) {
        String content = "";
        if (type == 1) {
            content = "您正在进行注册账号操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type == 2) {
            content = "您正在进行修改登录密码操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type == 3) {
            content = "您正在进行设置支付密码操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type == 4) {
            content = "您正在进行绑定邮箱操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type == 5) {
            content = "您正在进行提现操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type == 6) {
            content = "您正在进行转出操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type == 7) {
            content = "您正在进行重置交易密码操作，验证码为" + code + "。妥善保存，请勿转发!";
        }
        // 组装请求参数
        JSONObject map = new JSONObject();

        String url = "";
        if ("86".equals(areaCode) || "+86".equals(areaCode) || StringUtils.isEmpty(areaCode)) {
            url = smsConfig.getLocal().getUrl();
            map.put("account", smsConfig.getLocal().getUsername());
            map.put("password", smsConfig.getLocal().getPassword());
            map.put("msg", smsConfig.getLocal().getSign() + content);
        } else {
            /*String msg = "[" + smsConfig.getForeign().getSign()
                + "] Do not respond to any email/sms/phone call requesting you to reveal your TAC. TAC requested is "
                + code + ". ";
            url = smsConfig.getForeign().getUrl();
            map.put("account", smsConfig.getForeign().getUsername());
            map.put("password", smsConfig.getForeign().getPassword());
            map.put("msg", msg);*/
        }
        map.put("mobile", mobile);
        map.put("phone", mobile);

        String params = map.toString();

        SmsAppUtils.send(url, params);
    }
}