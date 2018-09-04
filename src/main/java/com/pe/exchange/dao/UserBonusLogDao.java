package com.pe.exchange.dao;

import com.pe.exchange.entity.UserBonusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBonusLogDao extends JpaRepository<UserBonusLog,Integer> {

    @Query(value = "select config from bonus_config limit 1",nativeQuery = true)
    String findConfig();

}
