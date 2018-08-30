package com.pe.exchange.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    private String userName; //用户名
    private String pwd; //密码
    private String telephone; //联系电话
    private String refereeId; //推荐人ID
    @ApiModelProperty(hidden = true)
    private String address;
    
    
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name = "id")
	private Set<UserPayInfo> userPayInfos;
    
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name = "id")
	private Set<DKOrder> dkOrders;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getRefereeId() {
		return refereeId;
	}
	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}
  
    
}
