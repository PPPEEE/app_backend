package com.pe.exchange.dao;

import com.pe.exchange.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeDao extends JpaRepository<Trade,Integer> {
}
