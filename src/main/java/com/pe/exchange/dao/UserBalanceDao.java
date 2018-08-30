package com.pe.exchange.dao;

import com.pe.exchange.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceDao extends JpaRepository<UserBalance,Integer> {
    String findAddressByUserIdAndCoinType(Integer userId,Integer coinType);
}
