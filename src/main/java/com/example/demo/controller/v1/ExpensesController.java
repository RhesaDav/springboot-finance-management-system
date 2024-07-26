package com.example.demo.controller.v1;

import com.example.demo.dto.ExpenseUpdateDTO;
import com.example.demo.dto.ResponseWrapper;
import com.example.demo.entity.Expense;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@Tag(name = "Expense Controller", description = "APIs related to Expense Entity")
public class ExpensesController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	@Operation(summary = "Create a new expense", description = "Creates a new expense for a given user")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Expense created successfully"),
			@ApiResponse(responseCode = "400", description = "User not found")
	})
	public ResponseEntity<ResponseWrapper<Expense>> createExpense(
			@RequestBody @Parameter(description = "Expense to be created") Expense expense,
			@RequestParam @Parameter(description = "User ID") String userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.createExpense(expense, user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@GetMapping
	@Operation(summary = "Get expenses by user", description = "Retrieves all expenses for a given user")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "User not found")
	})
	public ResponseEntity<ResponseWrapper<List<Expense>>> getExpenses(
			@RequestParam @Parameter(description = "User ID") String userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.getExpenseByUser(user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get expense by ID", description = "Retrieves a specific expense by its ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Expense retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "User not found")
	})
	public ResponseEntity<ResponseWrapper<Expense>> getExpense(
			@PathVariable @Parameter(description = "Expense ID") String id,
			@RequestParam @Parameter(description = "User ID") String userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.getExpenseById(id, user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@PatchMapping("/{id}")
	@Operation(summary = "Update an expense", description = "Updates an existing expense for a given user")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Expense updated successfully"),
			@ApiResponse(responseCode = "400", description = "User not found or bad request"),
			@ApiResponse(responseCode = "409", description = "Conflict occurred while updating")
	})
	public ResponseEntity<ResponseWrapper<Expense>> updateExpense(
			@PathVariable @Parameter(description = "Expense ID") String id,
			@RequestBody @Parameter(description = "Expense update data") ExpenseUpdateDTO expense,
			@RequestParam @Parameter(description = "User ID") String userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			ResponseWrapper<Expense> response = expenseService.updateExpense(id, expense, user);
			if (response.isSuccess()) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.badRequest().body(response);
			}
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an expense", description = "Deletes a specific expense for a given user")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
			@ApiResponse(responseCode = "400", description = "User not found or bad request")
	})
	public ResponseEntity<ResponseWrapper<Expense>> deleteExpense(
			@PathVariable @Parameter(description = "Expense ID") String id,
			@RequestParam @Parameter(description = "User ID") String userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			ResponseWrapper<Expense> response = expenseService.deleteExpense(id, user);
			if (response.isSuccess()) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.badRequest().body(response);
			}
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
}
