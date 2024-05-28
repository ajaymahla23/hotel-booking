package com.hotel.Repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.model.User;
import com.hotel.model.WithdrawalRequest;

@Repository
public interface WithdrawalRequestRepo extends JpaRepository<WithdrawalRequest, Long> {

	public WithdrawalRequest findByUserAndStatus(User user, String status);

	@Transactional
	@Modifying
	@Query("update WithdrawalRequest w set w.status=?1 where w.id=?2")
	public void updateAmountStatusforWithdraw(String status, Long id);

}
