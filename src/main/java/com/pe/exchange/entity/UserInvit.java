package com.pe.exchange.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户邀请
 */
@Data
@Entity
public class UserInvit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 邀请人
     */
    private Integer userId;
    /**
     * 受邀人
     */
    private Integer invitedUserId;
    /**
     * 第几层
     */
    private Integer level;
}
