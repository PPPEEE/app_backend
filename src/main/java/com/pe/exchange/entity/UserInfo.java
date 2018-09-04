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
    
}
