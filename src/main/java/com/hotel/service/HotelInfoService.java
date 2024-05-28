package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.Repo.HotelInfoRepo;
import com.hotel.model.HotelInfo;

@Service
public class HotelInfoService {
	@Autowired
	HotelInfoRepo hotelInfoRepo;

	public void addHotelInfo(HotelInfo HotelInfo) {
		hotelInfoRepo.save(HotelInfo);
	}

}
