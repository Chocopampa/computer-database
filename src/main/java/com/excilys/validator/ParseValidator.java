package com.excilys.validator;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParseValidator {

	private ParseValidator() {
	}

	private static final ParseValidator INSTANCE = new ParseValidator();
	private static final Logger log4j = LogManager.getLogger(ParseValidator.class.getName());

	public static ParseValidator getInstance() {
		return INSTANCE;
	}

	/**
	 * Check if the String is a long.
	 * 
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
				log4j.warn("The input provided is neither a long, nor 'null'.");
			}
		}
		return parsable;
	}

	/**
	 * Check if the String is a LocalDateTime
	 * 
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
				log4j.warn("The input provided is neither a LocalDateTime, nor 'null'.");
			}
		}
		return parsable;
	}

}
