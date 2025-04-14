package com.bookstore.service;

import org.springframework.stereotype.Service;

import com.bookstore.exceptions.BooksIdNotFoundException;
import com.bookstore.model.Book;

@Service
public class BookService {
	
	public Book getBookById(int id) {
		if(id!=1) {
			throw new BooksIdNotFoundException("Book with ID " + id + " not found");
			
		}
		
		return new Book(1,"java for Beginners");
	}

}
