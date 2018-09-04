package com.pe.exchange.entity;

import java.io.Serializable;

import javax.persistence.Entity;
<<<<<<< HEAD
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
=======
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84

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
	
<<<<<<< HEAD
	//订单状态 1:已完成 2：未完成 3:已失效
	private Integer status;
	
	//用户Id
	@ManyToOne
    @JoinColumn(name = "userId")
	private User user;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(Integer dealNumber) {
		this.dealNumber = dealNumber;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
=======
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
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
	
}
