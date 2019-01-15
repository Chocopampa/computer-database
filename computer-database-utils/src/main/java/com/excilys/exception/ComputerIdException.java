package com.excilys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ComputerIdException extends Exception{
	
	private static final String MESSAGE = "The id requested does not exist in database.";
	
	public ComputerIdException() {
		super(MESSAGE);
	}

}
