package com.pe.exchange.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
public class UserPayPwdInfo {
	
	@Id
	//用户信息
	@ApiModelProperty(hidden = true)
	private Integer userId;
	
	//支付密码
	@ApiModelProperty(value = "支付喵咪", position = 0)
	private String pwd;
	
	
}
