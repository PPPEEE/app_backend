package com.pe.exchange.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	//订单状态 1:已完成 2：未完成 3:已失效
	private Integer status;
	
	private Integer user_id;
	
}
