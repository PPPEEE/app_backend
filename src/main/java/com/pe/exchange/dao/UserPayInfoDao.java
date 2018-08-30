package com.pe.exchange.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pe.exchange.entity.UserPayInfo;

public interface UserPayInfoDao extends JpaRepository<UserPayInfo, Integer>{
	
	@Query(value="select * from user_pay_info where user_Id = :userId",nativeQuery = true)
	List<UserPayInfo> queryUserPayInfoList(@Param("userId") String userId);

}
