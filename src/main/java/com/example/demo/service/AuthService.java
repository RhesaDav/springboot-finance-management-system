package com.example.demo.service;

import com.example.demo.dto.AuthRequestDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public AuthResponseDTO login(AuthRequestDTO request, String deviceType) {
		User user = userRepository.findByUsername(request.getUsername());
		if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			String accessToken = jwtTokenProvider.createToken(user.getUsername(), deviceType, false);
			String refreshToken = jwtTokenProvider.createToken(user.getUsername(), deviceType, true);
			AuthResponseDTO response = new AuthResponseDTO();
			response.setAccessToken(accessToken);
			response.setRefreshToken(refreshToken);
			return response;
		}
		throw new RuntimeException("Invalid username or password");
	}
	
	public void register(AuthRequestDTO request) {
		if (userRepository.findByUsername(request.getUsername()) != null) {
			throw new RuntimeException("Username is already in use");
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		userRepository.save(user);
	}
	
	public AuthResponseDTO refresh(String refreshToken, String deviceType) {
		if (jwtTokenProvider.validateToken(refreshToken, true)) {
			String username = jwtTokenProvider.getUsername(refreshToken, true);
			String newAccessToken = jwtTokenProvider.createToken(username, deviceType, false);
			AuthResponseDTO response = new AuthResponseDTO();
			response.setAccessToken(newAccessToken);
			response.setRefreshToken(refreshToken);
			return response;
		}
		throw new RuntimeException("Invalid refresh token");
	}
}
