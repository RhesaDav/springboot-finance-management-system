package com.example.demo.controller.v2;

import com.example.demo.dto.ResponseWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.ExpenseService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/expense")
public class ExpenseController {
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/total")
	public ResponseEntity<ResponseWrapper<Double>> getTotalExpense(@RequestParam String userId, @RequestParam String period) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(expenseService.getTotalExpense(period, user));
		}
		return ResponseEntity.badRequest().body(new ResponseWrapper<>(false, "User not found", null));
	}
}
