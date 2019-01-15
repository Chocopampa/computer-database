package com.excilys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserCreationException extends Exception {

	private static final String MESSAGE = "User was not created.";
	
	public UserCreationException() {
		super(MESSAGE);
	}
}
