package com.hotel.Repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotel.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	public boolean existsByInvitationCode(Long invitationCode);

	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);

	public User findByEmail(String email);

	@Transactional
	@Modifying
	@Query("update User u set u.password=:password where u.id=:id")
	public void updateUserPassword(@Param("password") String password, @Param("id") Long id);

	public boolean existsByInvitationCode(String invitationCode);

	public boolean existsByEmailOrMobileNumber(String email, Long mobileNumber);

	public User findByInvitationCode(Long invitationCode);

}
