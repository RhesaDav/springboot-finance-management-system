package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
public class ResponseWrapper<T> {
	private Boolean success;
	@Getter
	private String message;
	@Getter
	private T data;
	
	public ResponseWrapper(Boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public Boolean isSuccess() {
		return success;
	}
	
}
