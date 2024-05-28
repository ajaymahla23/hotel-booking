package com.hotel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.model.MyBankCard;
import com.hotel.model.User;

@Repository
public interface MyBankCardRepo extends JpaRepository<MyBankCard, Long> {

	public MyBankCard findByUser(User user);

}
