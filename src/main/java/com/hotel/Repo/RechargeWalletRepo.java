package com.hotel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.model.RechargeWallet;

@Repository
public interface RechargeWalletRepo extends JpaRepository<RechargeWallet, Long> {

//	public List<RechargeWallet> findByStatusAndId(String status, Long id);
	public RechargeWallet findByStatusAndId(String status, Long id);

//	@Modifying
//	@Query("update RechargeWallet rw set rw.status=?1 where rw.id=?2")
//	public void updateAmountStatusByUser(String status, Long id);

	@Modifying
	@Query("update RechargeWallet rw set rw.status=?1,rw.afterAmt=?2 where rw.id=?3")
	public void updateAmountStatusByUser(String status, double afterAmt, Long id);

}
