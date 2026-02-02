package com.enotes.service;

import org.springframework.stereotype.Service;

import com.enotes.model.User;

@Service
public interface userService {

	 public User  getUserByEmail(String email);

	public Boolean ExistEmail(String email);

	User saveuser(User user);
	public User getUser(User user);
		
	
}
