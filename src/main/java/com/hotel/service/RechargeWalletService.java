package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.Repo.RechargeWalletRepo;
import com.hotel.model.RechargeWallet;

@Service
public class RechargeWalletService {

	@Autowired
	RechargeWalletRepo rechargeWalletRepo;

	public RechargeWallet addRechargeWallet(RechargeWallet rechargeWallet) {
		return rechargeWalletRepo.save(rechargeWallet);
	}

}
