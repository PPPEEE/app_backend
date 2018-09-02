package com.pe.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.exchange.dao.UserPayPwdDao;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.entity.UserPayPwdInfo;
import com.pe.exchange.utils.SHA256Util;
import com.pe.exchange.utils.UserUtil;

@Service
public class UserPayPwdService {

	@Autowired
	UserPayPwdDao userPayPwdDao;
	
	
	public boolean isExits(String... pwd) {
		Integer userId = UserUtil.get();
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
	
	public void updatePayPwd(String pwd) {
		Integer userId = UserUtil.get();
		UserPayPwdInfo upp = new UserPayPwdInfo();
		upp.setUserId(userId);
		upp.setPwd(encryptPwd(pwd));
		userPayPwdDao.save(upp);
	}
	private String encryptPwd(String pwd){
        return SHA256Util
            .sha256Str(pwd + SHA256Util.sha256Str(pwd));
   }
	
}
