package com.excilys.model;

public class Page {

	private long firstId;
	private int offset;
	
	public Page(long firstId, int offset) {
		this.firstId = firstId;
		this.offset = offset;
	}

	public long getFirstId() {
		return firstId;
	}
	public void setFirstId(long firstId) {
		this.firstId = firstId;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public Page next() {
		this.firstId += offset;
		return this;
	}
	
	public Page previous() {
		this.firstId -= offset;
		if (this.firstId < 0) {
			this.firstId = 0;
		}
		return this;
	}
	
	public Page pageNumber(int pageNumber) {
		this.firstId = offset * pageNumber;
		return this;
	}
}
