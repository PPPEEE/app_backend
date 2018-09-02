package com.pe.exchange.dao;

import com.pe.exchange.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserBalanceDao extends JpaRepository<UserBalance,Integer> {

    BigDecimal findBanalceByUserIdAndCoinType(Integer userId,Integer coin_type);
    @Modifying
    @Query(value = "update user_balance set balance=balance+:amount where user_id=:userId and coin_type=0",nativeQuery = true)
    int addDKBalance(@Param("userId") Integer userId,@Param("amount")BigDecimal amount);
    @Modifying
    @Query(value = "update user_balance set balance=balance-:amount where user_id=:userId and coin_type=0",nativeQuery = true)
    int subDKBalance(@Param("userId") Integer userId,@Param("amount")BigDecimal amount);
    @Modifying
    @Query(value = "update user_balance set balance=balance+:amount where user_id=:userId and coin_type=1",nativeQuery = true)
    int addDNBalance(@Param("userId") Integer userId,@Param("amount")BigDecimal amount);
    @Modifying
    @Query(value = "update user_balance set balance=balance-:amount where user_id=:userId and coin_type=1",nativeQuery = true)
    int subDNBalance(@Param("userId") Integer userId,@Param("amount")BigDecimal amount);

    @Query(value = "select a.*,b.user_level from user_balance a left join user b on a.user_id=b.id where a.coin_type=1 and a.balance>:amount and b.user_level<:level ",nativeQuery = true)
    List<UserBalance> findUpToLevelUser(@Param("level") Integer level,@Param("amount") Integer amount);
    @Query(value = "select sum(balance) from user_balance where coin_type=1 and user_id in(:users)",nativeQuery = true)
    BigDecimal findSumDnBalanceByUsers(@Param("users") List<Integer> users);
}
