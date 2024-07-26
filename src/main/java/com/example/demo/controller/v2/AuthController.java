package com.example.demo.controller.v2;

import com.example.demo.dto.AuthRequestDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login/{deviceType}")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request, @PathVariable String deviceType) {
		if (!"web".equalsIgnoreCase(deviceType) && !"mobile".equalsIgnoreCase(deviceType)) {
			throw new RuntimeException("Invalid device type");
		}
		return ResponseEntity.ok(authService.login(request, deviceType));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody AuthRequestDTO request) {
		authService.register(request);
		return ResponseEntity.ok("User registered successfully");
	}
}
