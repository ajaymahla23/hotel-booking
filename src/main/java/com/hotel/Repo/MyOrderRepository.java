package com.hotel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.model.MyOrder;

@Repository
public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

	public boolean existsByOrderNumber(String orderNumber);

}
