package com.bookstore.exceptions;

public class CustomerIdNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomerIdNotFoundException(String msg) {
		
		super(msg);
		
	}
	
	
	
}
