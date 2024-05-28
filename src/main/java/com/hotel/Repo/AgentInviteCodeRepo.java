package com.hotel.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.model.AgentInviteCode;
import com.hotel.model.User;

@Repository
public interface AgentInviteCodeRepo extends JpaRepository<AgentInviteCode, Long> {

	AgentInviteCode findByUser(User user);

//  @Query("SELECT a.user.id, a.user.fullName FROM AgentInviteCode a")
//  List<Object[]> findAllUserDetailsByAgents();

	@Query("SELECT a.user.invitationCode, a.user.id, COUNT(a.id) FROM AgentInviteCode a GROUP BY a.user.invitationCode, a.user.id")
	List<Object[]> countNewUsersByInvitationCodeAndUserId();

}
