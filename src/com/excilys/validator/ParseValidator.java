package com.excilys.validator;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class ParseValidator {

	private ParseValidator() {}
	
	private static final ParseValidator INSTANCE = new ParseValidator();
	
	public static ParseValidator getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * Check if the String is a long.
	 * @param toParse
	 * @return
	 */
	public boolean isParsableLong(String toParse) {
		boolean parsable = true;
		try {
			Long.parseLong(toParse);
		} catch (NumberFormatException e) {
			if (!"null".equalsIgnoreCase(toParse)) {
				parsable = false;
			}
		}
		return parsable;
	}
	
	/**
	 * Check if the String is a LocalDateTime
	 * @param toParse
	 * @return
	 */
	public boolean isParsableLocalDateTime(String toParse) {
		boolean parsable = true;
		try {
			LocalDateTime.parse(toParse);
		} catch (DateTimeException e) {
			if (!"null".equalsIgnoreCase(toParse)) {
				parsable = false;
			}
		}
		return parsable;
	}

	
}
