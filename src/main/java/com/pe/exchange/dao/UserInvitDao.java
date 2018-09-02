package com.pe.exchange.dao;

import com.pe.exchange.entity.UserInvit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInvitDao extends JpaRepository<UserInvit,Integer> {


    List<UserInvit> findByInvitedUserId(Integer invitedUserId);
    @Query(value = "select a.* from user_invit a left join user_balance b on a.invited_user_id=b.user_id and b.coin_type=1 and a.user_id=:userId where b.balance>:amount and a.invitLevel<=:level",nativeQuery = true)
    List<Integer> findInvitedUserIBydUserId(@Param("userId") Integer userId,@Param("amount") Integer amount,@Param("level") Integer invitLevel);
    @Query(value = "select a.*,b.user_level from user_invit a left join b on a.user_id=b.user_id where invitedUserId=:invitedUserId and invitLevel<=:invitLevel",nativeQuery = true)
    List<UserInvit> findByInvitedUserIdAndInvitLevel(@Param("invitedUserId") Integer invitedUserId,@Param("invitLevel") Integer invitLevel);
    UserInvit findByUserIdAndInvitedUserId(Integer userId,Integer invitedUserId);
    Integer findInvitOrderByUserIdAndInvitedUserId(Integer userId,Integer invitedUserId);
}
