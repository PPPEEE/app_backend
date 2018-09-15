package com.pe.exchange.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pe.exchange.entity.DKDealInfo;

@Repository
public interface DKDealDao extends JpaRepository<DKDealInfo, Integer>{

	@Query(value="select * from dkdeal_info where status in (0,1) and  user_id = :userId ",nativeQuery = true)
	List<DKDealInfo> getDKTotalNumber(@Param("userId")Integer userId);
	
	@Query(value="select count(1) from dkdeal_info where `status` = :status and type = :type and user_id != :userId",nativeQuery = true)
	Integer findTypeDKList(@Param("type")int type,@Param("userId")int userId,@Param("status") Integer status);
	
	@Query(value="select * from dkdeal_info where `status` = :status and type = :type and user_id != :userId  limit :page,:pageSize",nativeQuery = true)
	List<DKDealInfo> findTypeDKList(@Param("type")int type,@Param("userId")int userId,@Param("status") Integer status,@Param("page")Integer page,@Param("pageSize")Integer pageSize);
	
	@Query(value="select count(1) from dkdeal_info where user_id = :userId and status = :status",nativeQuery = true)
	Integer findUserDKList(@Param("userId")Integer userId,@Param("status")Integer status);
	
	
	@Query(value="select * from dkdeal_info where status = :status and user_id = :userId limit :page,:pageSize",nativeQuery = true)
	List<DKDealInfo> findUserDKList(@Param("userId")Integer userId,@Param("status") Integer status,@Param("page")Integer page,@Param("pageSize")Integer pageSize);
	
	@Query(value="select * from dkdeal_info where order_number = :orderNumber and user_id != :userId",nativeQuery = true)
	DKDealInfo findUserDKByNumber(@Param("orderNumber")String orderNumber ,@Param("userId") Integer userId);
	
	@Query(value="select * from dkdeal_info where order_number = :orderNumber and user_id = :userId",nativeQuery = true)
	DKDealInfo findUserDKByUserNumber(@Param("orderNumber")String orderNumber ,@Param("userId") Integer userId);
	
	@Query(value="SELECT SUM(deal_number) FROM dkdeal_info  where parent_order_number = :orderNumber ",nativeQuery = true)
	Integer findOrderNumberSum(@Param("orderNumber") String orderNumber);
	
}
