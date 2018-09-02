package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

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
    private Integer invitLevel;

    /**
     * 推荐顺序
     */
    private Integer invitOrder;



    private LocalDateTime addTime;

    /**
     * 用户级别,只读
     */
    private Integer userLevel;
}
