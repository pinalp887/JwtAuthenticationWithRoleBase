package com.cignex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cignex.entity.User;
import com.cignex.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public User findByName(String name) {
		return repository.findByName(name);
	}
}
