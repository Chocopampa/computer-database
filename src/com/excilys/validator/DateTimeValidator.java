package com.excilys.validator;

import java.time.LocalDateTime;

public class DateTimeValidator {
	
	private static ParseValidator parseValidator = ParseValidator.getInstance();
	
	private DateTimeValidator() {}
	
	private static final DateTimeValidator INSTANCE = new DateTimeValidator();
	
	public static DateTimeValidator getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Return the parsed LocalDateTime
	 * @param toCheck
	 * @return
	 */
	public LocalDateTime checkDateTime(String toCheck) {
		LocalDateTime dateTime = null;
		if (parseValidator.isParsableLocalDateTime(toCheck)) {
			dateTime = LocalDateTime.parse(toCheck);
		} else if (toCheck.isEmpty()){
			// Keep dateTime to null
		} else {
			System.out.println("Wrong date and time format (format : yyyy-mm-ddThh:mm:ss)");
		}
		return dateTime;
	}
}
