package com.pe.exchange.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
@Entity
public class DKDealInfo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//订单号
	private String orderNumber;
	
	//交易数量
	private Integer dealNumber;
	
	//实收金额
	private Double money;
	
	//交易类型
	private Integer type;
	
	//订单状态 1:已完成 2：未完成 3:等待付款 4:超时 5冻结 6已付款待确认 7申诉
	private Integer status;
	
	//最小限额
	@ColumnDefault(value = "0")
	private Integer minNumber;
	
	//付款时间 (分钟)
	private Integer times;
	
	private Integer user_id;
	
	//发布者信息
	@Transient
	private User user;
	
}
