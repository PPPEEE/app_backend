package com.pe.exchange.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.config.SmsConfig;
import com.pe.exchange.config.SwaggerConfig;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInfoDao;
import com.pe.exchange.dao.VersionInfoDao;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserBalance;
import com.pe.exchange.entity.UserInfo;
import com.pe.exchange.entity.VersionInfo;
import com.pe.exchange.exception.BaseException;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.exception.SysException;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.task.UserInvitTask;
import com.pe.exchange.utils.CopyBTCAddressUtil;
import com.pe.exchange.utils.PasswordUtil;
import com.pe.exchange.utils.SHA256Util;
import com.pe.exchange.utils.SmsAppUtils;
import com.pe.exchange.utils.TokenGeneratorUtil;
import com.pe.exchange.utils.UserUtil;
import com.pe.exchange.utils.VeriCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {




    private static final String VERI_CODE_FLAG = "veri";
    private static final String TOKEN_FLAG = "TOKEN";
    @Autowired
    SmsConfig smsConfig;
    @Autowired
    RedisOps redisOps;

    @Autowired
    UserDao userDao;

    @Autowired UserBalanceDao userBalanceDao;
    
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired UserInvitTask userInvitTask;
    
    @Autowired
    VersionInfoDao versionInfoDao; 
    
    
    public void updateUserInfo(UserInfo userInfo) {
    	try {
    		userInfo.setUserId(UserUtil.get().getId());
    		log.info("开始执行修改用户信息,用户ID："+userInfo.getUserId());
    		userInfoDao.save(userInfo);
		} catch (Exception e) {
			log.error("系统异常:",e);
			throw new SysException();
		}
    }
    public void updateUserInfo(UserInfo userInfo,User user) {
        try {
            userInfo.setUserId(user.getId());
            log.info("开始执行修改用户信息,用户ID："+userInfo.getUserId());
            userInfoDao.save(userInfo);
        } catch (Exception e) {
            log.error("系统异常:",e);
            throw new SysException();
        }
    }
    
    public UserInfo findUserInfo() {
    	User user =  UserUtil.get();
    	Optional<UserInfo> uInfo = userInfoDao.findById(user.getId());
    	if(uInfo.isPresent()) {
    		return uInfo.get();
    	}
    	return null;
    }
    

    public void getVeriCode(String areaCode, String mobile, int type) {
    	
        // 获取验证码
        long timeout=60L;

        String code = VeriCodeUtils.random(6);

        if(ProfileService.isTest) {
            code = "000000";
            timeout=timeout*10;
        }
        System.out.println(code);
        try {
            // 保存验证码到redis,有效期60s
            String key = mobile + VERI_CODE_FLAG;
            redisOps.setWithTimeout(key, code, timeout);
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

    
    public void updateUserPwd(String pwd,String newPwd) {
    	User u = UserUtil.get();
    	String userPwd = userDao.findById(u.getId()).get().getPwd();
    	if(userPwd.equals(PasswordUtil.encryptPwd(pwd,u.getAddress()))) {
    		u.setPwd(PasswordUtil.encryptPwd(newPwd,u.getAddress()));
    		userDao.save(u);
    	}else {
    		throw new BizException(ResultEnum.LOGIN_FAIL);
    	}
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void register(User user,String code){
        User u= userDao.findWithLogin(user.getUserName());
        if(u!=null){
            throw new BizException(ResultEnum.USER_ALREADY_EXISTS);
        }
        u= userDao.findWithLogin(user.getTelephone());
        if(u!=null){
            throw new BizException(ResultEnum.TELPHONE_ALREADY_EXISTS);
        }
        if(!checkVeriCode(user.getTelephone(),code)) {
        	throw new BizException(ResultEnum.CODE_ERROR);
        }
      

        try {
            userDao.save(user);
            UserInfo uInfo = new UserInfo();
            uInfo.setUserId(user.getId());
            uInfo.setMobile(user.getTelephone());
            updateUserInfo(uInfo,user);
            user.setAddress(CopyBTCAddressUtil.generateAddress(user.getId()));
            user.setUserLevel(0);
            user.setPwd(PasswordUtil.encryptPwd(user.getPwd(),user.getAddress()));
            userDao.save(user);
            initUserBalance(user);
            if(user.getRefereeId()!=null){
                userInvitTask.userInvit(user.getRefereeId(),user.getId());
            }

        } catch (Exception e) {
            log.error("数据插入失败",e);
            throw new SysException();
        }
    }

    private void initUserBalance(User user){
        List<UserBalance> list=new ArrayList<>();
        list.add(new UserBalance(null,user.getId(),0,"",new BigDecimal(0),new BigDecimal(0)));
        list.add(new UserBalance(null,user.getId(),1,"",new BigDecimal(0),new BigDecimal(0)));
        userBalanceDao.saveAll(list);

    }
    
    public void userNameExists(String userName) {
    	User u= userDao.findWithLogin(userName);
        if(u != null){
        	throw new BizException(ResultEnum.USER_ALREADY_EXISTS);
        }
    }
    
    public User findUserBy() {
    	User u = UserUtil.get();
    	u.setPwd("");
    	return u;
    }
    
    public void getUserPwd(String userName,String telephone,String code,String pwd) {
    	User u= userDao.findByUserName(userName);
        if(u==null){
            throw new BizException(ResultEnum.USER_NOT_EXISTS);
        }
        if(!u.getTelephone().equals(telephone)) {
        	throw new BizException(304,"联系方式错误！");
        }
        if(!checkVeriCode(u.getTelephone(),code) && (code != null || !"".equals(code))) {
        	throw new BizException(ResultEnum.CODE_ERROR);
        }
        u.setPwd(PasswordUtil.encryptPwd(pwd,u.getAddress()));
        userDao.save(u);
    }
    
    

    public String login(String username,String password){
        User user=userDao.findWithLogin(username);
        if(user==null||!PasswordUtil.encryptPwd(password,user.getAddress()).equals(user.getPwd()))
        {
            throw new BizException(ResultEnum.LOGIN_FAIL);
        }
        String token= TokenGeneratorUtil.generateValue();
        user.setPwd("");

        redisOps.setWithTimeout(token, JSON.toJSONString(user),  60 * 60 * 24 * 30L);
        return token;
    }
    

    public boolean checkVeriCode(String mobile, String code) {
        // 保存验证码到redis,有效期60s
        String key = mobile + VERI_CODE_FLAG;
        String savedCode = redisOps.get(key);
        if(ProfileService.isProd) {
            redisOps.delete(key);
        }
        return code.equals(savedCode);
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
    
    
    public VersionInfo getNewVersionInfo() {
    	return versionInfoDao.findById(1).get();
    }
    
    
    public List<User> findMyTeam(){
    	Integer id = UserUtil.get().getId();
    	List<User> users = userDao.findMyTeam(id);
    	for (User user : users) {
			user.setPwd("");
		}
    	return users;
    }
}
