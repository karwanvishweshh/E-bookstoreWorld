package com.bookstore.exceptions;

public class BooksIdNotFoundException extends RuntimeException 
{

	private static final long serialVersionUID = 1L;
	
	public BooksIdNotFoundException(String message) {
		super(message);
	}

}
