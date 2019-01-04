package com.excilys.validator;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseValidator {

	@Autowired
	public ParseValidator() {
	}

	private static final Logger LOG4J = LogManager.getLogger(ParseValidator.class.getName());

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
				LOG4J.warn("The input provided is neither a long, nor 'null'.");
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
		} catch (DateTimeException | NullPointerException e) {
			if (!"null".equalsIgnoreCase(toParse)) {
				parsable = false;
				LOG4J.warn("The input provided is neither a LocalDateTime, nor 'null'.");
			}
		}
		return parsable;
	}

}
