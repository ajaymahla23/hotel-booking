package com.hotel.Repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.model.User;
import com.hotel.model.Wallet;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {

	public Wallet findByUser(User user);

	@Modifying
	@Query("update Wallet w set w.walletAmount=?1 where w.user=?2")
	void updateAmountByUser(double walletAmount, User user);

	@Transactional
	@Modifying
	@Query("update Wallet w set w.walletAmount=?1 where w.id=?2")
	void updateWalletAmountById(double walletAmount, Long id);

}
