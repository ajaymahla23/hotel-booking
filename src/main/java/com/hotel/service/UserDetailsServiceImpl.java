package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hotel.Repo.UserRepo;
import com.hotel.model.CustomUserDetails;
import com.hotel.model.User;

import javax.transaction.Transactional;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//	@Autowired
//	private UserRepo userRepository;
//
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
////		  fetching user from DB
//
//		System.out.println("user name : " + username);
//
//		User user = userRepository.getUserByUserName(username);
////		User user = userRepository.getUserByUserName(username);
//
//		if (user == null) {
//			throw new UsernameNotFoundException("Could not found user!!!");
//		}
//		CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//		return customUserDetails;
//	}
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // fetching user from DB
        System.out.println("Fetching user by username: " + username);

        User user = userRepository.getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new CustomUserDetails(user);
    }

}
