package com.example.demo.controller.v1;

import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller", description = "APIs related to User Entity")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@Operation(summary = "Get all users", description = "Retrieves all users in the system")
	@ApiResponse(responseCode = "200", description = "Users retrieved successfully")
	public ResponseEntity<Map<String, Object>> getUsers() {
		List<User> users = userService.getAllUsers();
		Map<String, Object> response = new HashMap<>();
		response.put("users", users);
		response.put("total", users.size());
		response.put("message", "users retrieved successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping()
	@Operation(summary = "Create a new user", description = "Creates a new user in the system")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public ResponseEntity<Map<String, Object>> addUser(
			@Valid @Parameter(description = "User to be created") @RequestBody User user) {
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
	@Operation(summary = "Update a user", description = "Updates an existing user in the system")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "409", description = "Conflict occurred while updating")
	})
	public ResponseEntity<Map<String, Object>> updateUser(
			@PathVariable @Parameter(description = "User ID") String id,
			@Valid @RequestBody @Parameter(description = "User update data") UserUpdateDTO user) {
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
	@Operation(summary = "Delete a user", description = "Deletes a specific user from the system")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request")
	})
	public ResponseEntity<Map<String, Object>> deleteUser(
			@PathVariable @Parameter(description = "User ID") String id) {
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
