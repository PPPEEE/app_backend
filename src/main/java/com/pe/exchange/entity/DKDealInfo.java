package com.pe.exchange.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@Entity
public class DKDealInfo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @ApiModelProperty(hidden = true)
	private Integer id;
	
	//订单号
	 @ApiModelProperty(value = "订单号",position = 0)
	private String orderNumber;
	
	//交易数量
	@ApiModelProperty(value = "交易数量",position = 1)
	private Integer dealNumber;
	
	//实收金额
	@ApiModelProperty(value = "实收金额",position = 2)
	private Double money;
	
	//交易类型
	@ApiModelProperty(value = "交易类型",position = 3)
	private Integer type;
	
	//订单状态  0:取消 1:已完成 2：未完成 3:等待付款 4:超时 5冻结 6已付款待确认 7申诉
	@ApiModelProperty(value = "交易状态",position = 3)
	private Integer status;
	
	//支付方式
	@ApiModelProperty(value = "支付方式",position = 4)
	private String payInfo;
	
	//最小限额
	@ColumnDefault(value = "0")
	@ApiModelProperty(value = "最小限额",position = 5)
	private Integer minNumber;
	
	//付款时间 (分钟)
	@ApiModelProperty(value = "付款时间(分钟)",position = 6)
	private Integer times;
	
	//父类订单
	private String parentOrderNumber;
	
	//订单总额
	private Integer totalMoney;
	
	@ApiModelProperty(hidden = true)
	private Integer user_id;
	
	private Integer pay_user_id = -1;
	
	//发布者信息
	@Transient
	private User user;
	
	//发布者信息
	@Transient
	private User payUser;
	
}
