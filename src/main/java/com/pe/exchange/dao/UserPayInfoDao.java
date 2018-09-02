package com.pe.exchange.dao;

import com.pe.exchange.entity.UserPayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPayInfoDao extends JpaRepository<UserPayInfo, Integer>{
	
	@Query(value="select * from user_pay_info where user_Id = :userId",nativeQuery = true)
	List<UserPayInfo> queryUserPayInfoList(@Param("userId") Integer userId);

}
