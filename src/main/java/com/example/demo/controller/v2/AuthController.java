package com.example.demo.controller.v2;

import com.example.demo.dto.AuthRequestDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.RefreshRequestDTO;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth")
@Tag(name = "Auth Controller", description = "APIs related to Authentication")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login/{deviceType}")
	@Operation(
			summary = "Login user",
			description = "Logs in a user based on device type (web or mobile)",
			parameters = {
					@Parameter(name = "deviceType", description = "Device type (web or mobile)", required = true, example = "web")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "User logged in successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid device type"),
					@ApiResponse(responseCode = "500", description = "Internal server error")
			}
	)
	public ResponseEntity<AuthResponseDTO> login(
			@RequestBody @Parameter(description = "Login request data", required = true) AuthRequestDTO request,
			@PathVariable String deviceType) {
		if (!"web".equalsIgnoreCase(deviceType) && !"mobile".equalsIgnoreCase(deviceType)) {
			throw new RuntimeException("Invalid device type");
		}
		return ResponseEntity.ok(authService.login(request, deviceType));
	}
	
	@PostMapping("/register")
	@Operation(
			summary = "Register new user",
			description = "Registers a new user",
			responses = {
					@ApiResponse(responseCode = "200", description = "User registered successfully"),
					@ApiResponse(responseCode = "500", description = "Internal server error")
			}
	)
	public ResponseEntity<String> register(
			@RequestBody @Parameter(description = "Registration request data", required = true) AuthRequestDTO request) {
		authService.register(request);
		return ResponseEntity.ok("User registered successfully");
	}
	
	@PostMapping("/refresh/{deviceType}")
	@Operation(
			summary = "Refresh token",
			description = "Refreshes the authentication token based on device type (web or mobile)",
			parameters = {
					@Parameter(name = "deviceType", description = "Device type (web or mobile)", required = true, example = "web")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid device type"),
					@ApiResponse(responseCode = "500", description = "Internal server error")
			}
	)
	public ResponseEntity<AuthResponseDTO> refresh(
			@RequestBody @Parameter(description = "Refresh request data", required = true) RefreshRequestDTO request,
			@PathVariable String deviceType) {
		if (!"web".equalsIgnoreCase(deviceType) && !"mobile".equalsIgnoreCase(deviceType)) {
			throw new RuntimeException("Invalid device type");
		}
		return ResponseEntity.ok(authService.refresh(request.getRefreshToken(), deviceType));
	}
}
