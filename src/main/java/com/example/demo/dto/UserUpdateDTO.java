package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDTO {
	@Size(min = 3, max = 50)
	private String username;
	
	@Size(min = 3, max = 50)
	private String name;
	
	@Email
	private String email;
	
	@Size(min = 6, max = 100)
	private String password;
	
}
