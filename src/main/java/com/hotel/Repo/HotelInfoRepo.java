package com.hotel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.model.HotelInfo;
@Repository
public interface HotelInfoRepo extends JpaRepository<HotelInfo, Long> {

}
