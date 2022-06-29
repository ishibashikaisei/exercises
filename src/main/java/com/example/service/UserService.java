package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> receiveUser(String mail){
		return userRepository.receiveUser(mail);
	}
	
	public void userSet(User user) {
		userRepository.userSet(user);
	}
}
