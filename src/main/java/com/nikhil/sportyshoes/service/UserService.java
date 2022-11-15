package com.nikhil.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nikhil.sportyshoes.model.User;
import com.nikhil.sportyshoes.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void registerUser(User user)
	{
		passwordEncoder.encode(user.getPassword());
		repository.save(user);
	}
	
	public Optional<User> findUserByEmail(String email)
	{
		return repository.findByEmail(email);
	}
	
	public List<User> getAllRegisteredUser()
	{
		return repository.findAll();
	}
}
