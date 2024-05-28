package com.hotel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.model.RoleType;

@Repository
public interface RoleTypeRepo extends JpaRepository<RoleType, Long> {

}
