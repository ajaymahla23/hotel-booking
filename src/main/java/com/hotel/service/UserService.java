package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.Repo.UserRepo;
import com.hotel.model.User;

import javax.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	@Transactional
	public void addUserRegister(User user) {
		userRepo.save(user);
	}

}
