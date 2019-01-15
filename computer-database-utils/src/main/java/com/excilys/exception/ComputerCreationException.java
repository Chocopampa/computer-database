package com.excilys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ComputerCreationException extends Exception{

	private static final String MESSAGE = "Computer creation failed.";
	
	public ComputerCreationException() {
		super(MESSAGE);
	}

}
