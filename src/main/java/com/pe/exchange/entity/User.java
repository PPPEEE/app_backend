package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @MapKey
    private String userName;
    private String mobile;
    private String password;
    private String payPassword;
    private String inviter;
    private LocalDateTime addTime;
    private LocalDateTime lastUpdTime;
    private int status;
}
