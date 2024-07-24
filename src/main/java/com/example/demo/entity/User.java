package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity()
@Table(name = "users")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "username is required")
	@Size(min = 6, max = 20, message = "username min 6 and nax 20")
	private String username;
	
	private String name;
	
	@NotBlank(message = "email is required")
	@Email(message = "email not valid")
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 6, message = "password min 6")
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Expense> expenses;
	
	@CreationTimestamp
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	private ZonedDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = ZonedDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = ZonedDateTime.now();
	}
}
