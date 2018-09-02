package com.pe.exchange.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private  Integer userLevel;
    
    @Transient
    private List<UserPayInfo> userPayInfo;
    
}
