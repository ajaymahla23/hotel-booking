package com.hotel.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.Repo.RechargeWalletRepo;
import com.hotel.Repo.WalletRepo;
import com.hotel.Repo.WithdrawalRequestRepo;
import com.hotel.model.RechargeWallet;
import com.hotel.model.User;
import com.hotel.model.Wallet;
import com.hotel.model.WithdrawalRequest;

@Service
public class WalletService {
	@Autowired
	WithdrawalRequestRepo withdrawalRequestRepo;
	@Autowired
	RechargeWalletRepo rechargeWalletRepo;
	@Autowired
	WalletRepo walletRepo;

	public WithdrawalRequest addWithdrawReq(WithdrawalRequest withdrawalRequest) {
		return this.withdrawalRequestRepo.save(withdrawalRequest);
	}

	public Wallet addWalletAmtAndId(Wallet wallet, String roleType, Long id) {
//		List<RechargeWallet> rechargeWalletList = rechargeWalletRepo.findByStatusAndId("N", id);
		RechargeWallet rechargeWalletList = rechargeWalletRepo.findByStatusAndId("N", id);
		rechargeWalletList.setStatus("Y");
		rechargeWalletList.setAfterAmt(rechargeWalletList.getAmountByUser());
//		for (RechargeWallet rechargeWallet : rechargeWalletList) {
//			rechargeWallet.setStatus("Y");
//		}
//		rechargeWalletRepo.saveAll(rechargeWalletList);
		rechargeWalletRepo.save(rechargeWalletList);

		wallet.setBookPass(16L);
		wallet.setUser(rechargeWalletList.getUser());
		wallet.setWalletAmount(rechargeWalletList.getAmountByUser());

		return this.walletRepo.save(wallet);
	}

	@Transactional
	public void updateAmountByUser(double walletAmount, User user, Long id) {
		rechargeWalletRepo.updateAmountStatusByUser("Y", walletAmount, id);
		walletRepo.updateAmountByUser(walletAmount, user);
	}

	public void updateTotalWalletAmt(Long withdrawReqid) {
		WithdrawalRequest withdrawalRequest = withdrawalRequestRepo.findById(withdrawReqid).get();
		Wallet wallet = walletRepo.findByUser(withdrawalRequest.getUser());
		double withdrawlReqAmt = withdrawalRequest.getWithdrawAmout();
		double pendingTotalAmtInWallet = wallet.getWalletAmount() - withdrawlReqAmt;
		withdrawalRequestRepo.updateAmountStatusforWithdraw("Y", withdrawReqid);
		walletRepo.updateWalletAmountById(pendingTotalAmtInWallet, wallet.getId());
	}

}
