package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class UserInfo implements Serializable{
    @Id
    private Integer userId;
    private String avatar;
    private String nickName;
    private String realName;
    private String gender;
    private String birthday;
    private String addr;
    private String mobile;
    private LocalDateTime lastUpdTime;
<<<<<<< HEAD
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public LocalDateTime getLastUpdTime() {
		return lastUpdTime;
	}
	public void setLastUpdTime(LocalDateTime lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}
    
=======
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
    
}
