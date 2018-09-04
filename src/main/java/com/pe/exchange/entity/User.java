package com.pe.exchange.entity;

<<<<<<< HEAD
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
=======
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
<<<<<<< HEAD
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName; //用户名
    private String pwd; //密码
    private String telephone; //联系电话
    private String refereeId; //推荐人ID
    
    
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
  
=======
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(indexes = {@Index(columnList = "address"),@Index(columnList = "userName"),@Index(columnList = "telephone")})
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    @Column(length = 32)
    private String userName; //用户名
    @Column(length = 32)
    private String pwd; //密码
    @Column(length = 32)
    private String telephone; //联系电话
    @Column(length = 32)
    private String refereeId; //推荐人ID
    @ApiModelProperty(hidden = true)
    @Column(length = 64)
    private String address;

    /**
     * 级别
     * 0、临时用户
     * 1、普通用户
     * 2、VIP
     */
    @ColumnDefault("0")
    @Column(insertable = false)
    private  Integer userLevel;
    
    //1：会员 2：普通用户
    private String type;
    
    @Transient
    private List<UserPayInfo> userPayInfo;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
    
}
