package com.pe.exchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pe.exchange.entity.Appeal;
import org.springframework.stereotype.Repository;

@Repository
public interface DKDealAppealDao extends JpaRepository<Appeal, Integer>{

}
