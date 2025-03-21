package com.sheryians.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheryians.java.model.User;
import com.sheryians.java.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	public void removeUserById(Integer id) {
		userRepository.deleteById(id);
	}
	
}
