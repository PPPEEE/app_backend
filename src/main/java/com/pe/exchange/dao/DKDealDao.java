package com.pe.exchange.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pe.exchange.entity.DKDealInfo;

@Repository
public interface DKDealDao extends JpaRepository<DKDealInfo, Integer>{

	@Query(value="select * from dkdeal_info where status = 1 and user_id = :userId ",nativeQuery = true)
	List<DKDealInfo> getDKTotalNumber(@Param("userId")String userId);
	
	@Query(value="select * from dkdeal_info where `status` = 1 and type = :type",nativeQuery = true)
	List<DKDealInfo> findTypeDKList(@Param("type")int type);
	
}
