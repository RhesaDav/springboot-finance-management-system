package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ExpenseUpdateDTO {
	@NotBlank(message = "Description is required")
	private String description;
	
	@PastOrPresent(message = "Date must be in the past or present")
	private Date date;
	
	@NotNull(message = "Amount is required")
	@Min(value = 1, message = "Amount must be at least 1")
	private Double amount;
	
}
