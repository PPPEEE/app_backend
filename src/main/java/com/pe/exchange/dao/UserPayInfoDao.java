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
	
	/*@Query (value = "UPDATE user_pay_info SET account_id =:accountId,account_name =:accountName,bank =:bank ,bank_branch =:bankBranch ,pay_type=:payType,qr_code=:qrCode where id = :id and user_Id = :userId")
	void updatePayInfo(@Param("accountId") String accountId,@Param("accountName") String accountName,@Param("bank") String bank,@Param("bankBranch")String bankBranch,@Param("payType") Integer payType, @Param("qrCode") String qrCode,@Param("id")Integer id,@Param("userId") Integer userId);*/

}
