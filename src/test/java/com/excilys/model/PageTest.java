package com.excilys.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PageTest {
	
	private static final long FIRST_ID = 10;
	private static final int OFFSET = 10;

	@Test
	public void testPage() {
		Page page = new Page(FIRST_ID, OFFSET);
		assertEquals(FIRST_ID, page.getFirstId());
		assertEquals(OFFSET, page.getOffset());
	}

	@Test
	public void testNext() {
		Page page = new Page(FIRST_ID, OFFSET);
		page = page.next();
		
		long newFirstId = OFFSET + FIRST_ID; 
		assertEquals(newFirstId, page.getFirstId());
	}

	@Test
	public void testPrevious() {
		Page page = new Page(FIRST_ID, OFFSET);
		page = page.previous();
		
		long newFirstId = FIRST_ID - OFFSET; 
		assertEquals(newFirstId, page.getFirstId());
	}
	
	@Test
	public void testPreviousNegative() {
		Page page = new Page(FIRST_ID, OFFSET);
		page = page.previous();
		long newFirstId = FIRST_ID - OFFSET;
		
		page = page.previous();
		newFirstId = newFirstId - OFFSET; 
		
		assertEquals("The id cannot be negative.", 0, page.getFirstId());
	}

	@Test
	public void testPageNumber() {
		Page page = new Page(FIRST_ID, OFFSET);
		int pageNumber = 4;
		page = page.pageNumber(pageNumber);
		
		long newFirstId = OFFSET * pageNumber; 
		assertEquals(newFirstId, page.getFirstId());
	}

}
