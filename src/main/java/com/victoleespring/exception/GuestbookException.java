package com.victoleespring.exception;

public class GuestbookException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public GuestbookException() {
		super("GuestBookDAOException Occurs");
	}
	
	public GuestbookException(String msg) {
		super(msg);
	}
}
