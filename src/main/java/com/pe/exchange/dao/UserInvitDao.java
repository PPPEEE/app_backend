package com.pe.exchange.dao;

import com.pe.exchange.entity.UserInvit;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInvitDao extends JpaRepository<UserInvit,Integer> {

    List<UserInvit> findByInvitedUserId(Integer invitedUserId);

}
