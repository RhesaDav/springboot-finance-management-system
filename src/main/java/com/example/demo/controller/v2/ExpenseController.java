package com.example.demo.controller.v2;

import com.example.demo.dto.ResponseWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.ExpenseService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/expense")
@Tag(name = "Expense Controller", description = "APIs related to Expense Entity")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/total")
	@Operation(summary = "Get total expense", description = "Retrieves the total expense for a user for a specified period")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Total expense retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "User not found or bad request")
	})
	public ResponseEntity<ResponseWrapper<Double>> getTotalExpense(
			@RequestParam @Parameter(description = "User ID") String userId,
			@RequestParam @Parameter(description = "Period (e.g., monthly, yearly)") String period) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.getTotalExpense(period, user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
}
