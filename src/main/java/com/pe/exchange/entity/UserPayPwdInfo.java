package com.pe.exchange.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserPayPwdInfo {
	
	@Id
	//用户信息
	private Integer userId;
	
	//支付密码
	private String pwd;
	
	
}
