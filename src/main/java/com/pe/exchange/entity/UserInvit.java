package com.pe.exchange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
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


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false,columnDefinition = "datetime not NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addTime;

    /**
     * 用户级别,只读
     */
    @Transient
    private Integer userLevel;
}
