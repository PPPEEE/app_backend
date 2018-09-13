package com.pe.exchange.dao;

import com.pe.exchange.entity.UserBonusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBonusLogDao extends JpaRepository<UserBonusLog,Integer> {

    @Query(value = "select config from bonus_config limit 1",nativeQuery = true)
    String findConfig();

    @Query(value = "select * from user_bonus_log where status=1 and user_id=:userId and bonus_coin_type=:coinType and amount>=0 order by id desc limit :offset,:pageSize  ",nativeQuery = true)
    List<UserBonusLog> findBalanceIncomeList(@Param("coinType") int coinType,@Param("userId") Integer userId,@Param("offset") int offset,@Param("pageSize")int pageSize);

    @Query(value = "select * from user_bonus_log where status=1 and user_id=:userId  and bonus_coin_type=:coinType and amount<0 order by id desc limit :offset,:pageSize  ",nativeQuery = true)
    List<UserBonusLog> findBalanceOutlayList(@Param("coinType") int coinType,@Param("userId") Integer userId,@Param("offset") int offset,@Param("pageSize")int pageSize);

    @Query(value = "select * from user_bonus_log where status=1 and user_id=:userId  and bonus_coin_type=:coinType order by id desc limit :offset,:pageSize ",nativeQuery = true)
    List<UserBonusLog> findBalanceList(@Param("coinType") int coinType,@Param("userId") Integer userId,@Param("offset") int offset,@Param("pageSize")int pageSize);

    @Query(value = "select * from user_bonus_log where status=1 and user_id=:userId  and bonus_coin_type=1 and bonus_type=5 order by id desc limit :offset,:pageSize ",nativeQuery = true)
    List<UserBonusLog> findDNReleaseList(@Param("userId") Integer userId,@Param("offset") int offset,@Param("pageSize")int pageSize);

    @Query(value = "select * from user_bonus_log where status=1 and user_id=:userId  and bonus_coin_type=0 and bonus_type=0 order by id desc limit :offset,:pageSize ",nativeQuery = true)
    List<UserBonusLog> findDKForzenList(@Param("userId") Integer userId,@Param("offset") int offset,@Param("pageSize")int pageSize);

    @Query(value = "select * from user_bonus_log where status=1 and user_id=:userId  and bonus_coin_type=1 and (bonus_type=3 or bonus_type=4) order by id desc limit :offset,:pageSize ",nativeQuery = true)
    List<UserBonusLog> findDNSpeedUpList(@Param("userId") Integer userId,@Param("offset") int offset,@Param("pageSize")int pageSize);
}
