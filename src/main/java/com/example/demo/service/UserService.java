package com.example.demo.service;

import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}
	
	@Transactional
	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistException("User Already Exist with email: " + user.getEmail());
		}
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistException("User Already Exist with username: " + user.getUsername());
		}
		return userRepository.save(user);
	}
	
	@Transactional
	public User updateUser(String id, UserUpdateDTO userDetails) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
		
		if (user.getUsername() != null && user.getUsername().equals(userDetails.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistException("User Already Exist with username: " + user.getUsername());
		}
		
		if (user.getEmail() != null && !user.getEmail().equals(userDetails.getEmail()) && userRepository.existsByEmail(userDetails.getEmail())) {
			throw new UserAlreadyExistException("User Already Exist with email: " + user.getEmail());
		}
		
		if (userDetails.getName() != null) {
			user.setName(userDetails.getName());
		}
		if (userDetails.getEmail() != null) {
			user.setEmail(userDetails.getEmail());
		}
		if (userDetails.getPassword() != null) {
			user.setPassword(userDetails.getPassword());
		}
		if (userDetails.getUsername() != null) {
			user.setUsername(userDetails.getUsername());
		}
		
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteUser(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
		userRepository.delete(user);
	}
}