package com.pe.exchange.entity;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * 奖励比例,统一使用千分之,具体比较直接填int值
 */
@Data
public class UserBonusConfig {
    /***
     * DN释放成DK的比例,默认0.2%
     */
    private int dn2dk;
    /**
     * DK转成DN的兑换比例,默认6倍,即600%
     */
    private int dk2dn;
    /**
     * 直推奖励
     */
    private Invit invit=new Invit();
    private Team team=new Team();

    @Data
    public  static class Invit{
        /**
         * 直推DN奖励,默认80%
         */
        private int dn;
        /**
         * 直推DK奖励,按推荐人数决定
         */
        private Map<Integer,Integer> dk=new HashMap<>();

    }
    @Data
    public static class Team{
        /**
         * 团队兑换奖励代数
         */
        private int exchangeLevel;
        /**
         * 团队兑换奖励比例,按用户等级区分
         */
        private Map<Integer,Integer> exchange=new HashMap<>();
        /**
         * 团队流通奖励代数
         */
        private int circLevel;
        /**
         * 团队流通奖励比例,按用户等级区分
         */
        private Map<Integer,Integer> circ=new HashMap<>();
    }

    public static void main(String[] args) {
        UserBonusConfig config=new UserBonusConfig();
        config.setDn2dk(3);
        config.setDk2dn(6000);
        config.getInvit().setDn(800);
        config.getInvit().getDk().put(1,50);
        config.getInvit().getDk().put(2, 60);
        config.getInvit().getDk().put(3, 70);
        config.getInvit().getDk().put(4,100);
        config.getTeam().getExchange().put(1, 10);
        config.getTeam().getExchange().put(2, 30);
        config.getTeam().getCirc().put(1, 4);
        config.getTeam().getCirc().put(2, 7);

        System.out.println(JSON.toJSONString(config));
    }
}
