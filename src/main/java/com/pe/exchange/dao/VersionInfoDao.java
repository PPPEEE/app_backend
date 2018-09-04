package com.pe.exchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pe.exchange.entity.VersionInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionInfoDao extends JpaRepository<VersionInfo, Integer>{

}
