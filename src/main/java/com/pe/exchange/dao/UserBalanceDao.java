package com.pe.exchange.dao;

import com.pe.exchange.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceDao extends JpaRepository<UserBalance,Integer> {

    Integer findBanalceByUserId(Integer userId);
    @Query(value = "update user_balance set balance=balance+:amount where user_id=:userId",nativeQuery = true)
    int addBalance(@Param("userId") Integer userId,@Param("amount")Integer amount);
    @Query(value = "update user_balance set balance=balance-:amount where user_id=:userId",nativeQuery = true)
    int subBalance(@Param("userId") Integer userId,@Param("amount")Integer amount);
}
