package com.pe.exchange.dao;

import com.pe.exchange.entity.TransferLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferLogDao  extends JpaRepository<TransferLog,Integer> {
    List<TransferLog> findByBonusStatus(Integer BonusStatus);
}
