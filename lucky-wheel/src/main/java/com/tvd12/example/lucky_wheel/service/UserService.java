package com.tvd12.example.lucky_wheel.service;

import com.tvd12.example.lucky_wheel.entity.User;

import java.util.List;

public interface UserService {
	
	void saveUser(User user);
	
	User createUser(String username, String password);
	
	User getUser(String username);
	
	List<User> getAllUsers();
}
