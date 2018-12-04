package com.excilys.validator;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParseValidatorTest {

	private ParseValidator parseValidator = ParseValidator.getInstance();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsParsableLong() {
		String intToParse = "3";
		String nullToParse = "null";
		String stringToParse = "abcde";

		Assert.assertTrue(parseValidator.isParsableLong(intToParse));
		Assert.assertTrue(parseValidator.isParsableLong(nullToParse));
		Assert.assertFalse(parseValidator.isParsableLong(stringToParse));
		Assert.assertFalse(parseValidator.isParsableLong(null));
	}

	@Test
	public void testIsParsableLocalDateTime() {
		String dateToParse = "2000-10-10T00:00:00";
		String nullToParse = "null";
		String stringToParse = "abcde";

		Assert.assertTrue(parseValidator.isParsableLocalDateTime(dateToParse));
		Assert.assertTrue(parseValidator.isParsableLocalDateTime(nullToParse));
		Assert.assertFalse(parseValidator.isParsableLocalDateTime(stringToParse));
		Assert.assertFalse(parseValidator.isParsableLocalDateTime(null));
	}

}
