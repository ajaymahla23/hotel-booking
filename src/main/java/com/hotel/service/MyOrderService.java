package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.Repo.MyOrderRepository;

@Service
public class MyOrderService {
	@Autowired
	MyOrderRepository myOrderRepo;

}
