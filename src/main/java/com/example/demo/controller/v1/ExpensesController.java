package com.example.demo.controller.v1;

import com.example.demo.dto.ExpenseUpdateDTO;
import com.example.demo.dto.ResponseWrapper;
import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.service.ExpenseService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpensesController {
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<ResponseWrapper<Expense>> createExpense(@RequestBody Expense expense, @RequestParam Long userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.createExpense(expense, user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@GetMapping
	public ResponseEntity<ResponseWrapper<List<Expense>>> getExpenses(@RequestParam Long userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.getExpenseByUser(user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseWrapper<Expense>> getExpense(@PathVariable Long id, @RequestParam Long userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.getExpenseById(id, user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ResponseWrapper<Expense>> updateExpense(@PathVariable Long id, @RequestBody ExpenseUpdateDTO expense, @RequestParam Long userId) {
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
	public ResponseEntity<ResponseWrapper<Expense>> deleteExpense(@PathVariable Long id, @RequestParam Long userId) {
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
