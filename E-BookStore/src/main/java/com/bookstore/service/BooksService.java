package com.bookstore.service;

import java.util.List;

import com.bookstore.entities.Book;

public interface BooksService {
	
	public Book createdOrUpdatebook(Book booksModule);

	public Book getBookId(Long bookId);

	public List<Book>  getByAllBookss();


}
