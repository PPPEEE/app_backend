package com.pe.exchange.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

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

    /**
     * 级别
     * 0、临时用户
     * 1、普通用户
     * 2、VIP
     */
    @ColumnDefault("0")
    private  Integer userLevel;
    
    @Transient
    private List<UserPayInfo> userPayInfo;
    
}
