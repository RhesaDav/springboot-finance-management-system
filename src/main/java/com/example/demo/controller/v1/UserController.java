package com.example.demo.controller.v1;

import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getUsers() {
		List<User> users = userService.getAllUsers();
		Map<String, Object> response = new HashMap<>();
		response.put("users", users);
		response.put("total", users.size());
		response.put("message", "users retrieved successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> addUser(@Valid @RequestBody User user) {
		try {
			User createdUser = userService.createUser(user);
			Map<String, Object> response = new HashMap<>();
			response.put("message", "user created successfully");
			response.put("user", createdUser);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateDTO user) {
		try {
			User updatedUser = userService.updateUser(id, user);
			Map<String, Object> response = new HashMap<>();
			response.put("message", "user updated successfully");
			response.put("user", updatedUser);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (UserAlreadyExistException | ResourceNotFoundException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		try {
			userService.deleteUser(id);
			Map<String, Object> response = new HashMap<>();
			response.put("message", "user deleted successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
}
