package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class BonusRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 直推DN奖励
     */
    private Integer invitDN;
    /**
     * 直推DK奖励1人
     */
    private Integer invitNK1;
    /**
     * 直推DK奖励2人
     */
    private Integer invitNK2;
    /**
     * 直推DK奖励3人
     */
    private Integer invitNK3;
    /**
     * 直推DK奖励4人及以上
     */
    private Integer invitNK4;
    /**
     * 团队兑换奖励
     */
    private Integer teamExchange;
    /**
     * 团队兑换奖励vip
     */
    private Integer teamExchangeVip;
    /**
     * 团队流通奖励
     */
    private Integer teamCirc;
    /**
     * 团队流通奖励vip
     */
    private Integer teamCircVip;
}
