package com.pe.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.UserPayPwdDao;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.entity.UserPayPwdInfo;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.utils.SHA256Util;
import com.pe.exchange.utils.UserUtil;

@Service
public class UserPayPwdService {

	@Autowired
	UserPayPwdDao userPayPwdDao;
	
	@Autowired
	UserService userService;
	
	public boolean isExits(String... pwd) {
		Integer userId = UserUtil.get().getId();
		UserPayInfo u= null;
		if(pwd.length>0) {
			u = userPayPwdDao.queryPayPwdExists(userId,pwd[0]);
		}else {
			u = userPayPwdDao.queryPayPwdExists(userId);
		}
		if(u == null) {
			return false;
		}
		return true;
	}
	
	public void updatePayPwd(String pwd,String code) {
		User user = UserUtil.get();
		
		if(!userService.checkVeriCode(user.getTelephone(), code)) {
			throw new BizException(ResultEnum.CODE_ERROR);
		}
		
		String reg = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,32}$/";
		if(!pwd.matches(reg)) {
			throw new BizException(307,"请输入8-32位又大小写数字或符号组成的密码！");
		}
		UserPayPwdInfo upp = new UserPayPwdInfo();
		upp.setUserId(user.getId());
		upp.setPwd(encryptPwd(pwd));
		userPayPwdDao.save(upp);
	}
	private String encryptPwd(String pwd){
        return SHA256Util
            .sha256Str(pwd + SHA256Util.sha256Str(pwd));
   }
	
}
