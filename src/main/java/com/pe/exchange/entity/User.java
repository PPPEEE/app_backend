package com.pe.exchange.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    
    //1：会员 2：普通用户
    private String type;
    
    @Transient
    private List<UserPayInfo> userPayInfo;
    
}
