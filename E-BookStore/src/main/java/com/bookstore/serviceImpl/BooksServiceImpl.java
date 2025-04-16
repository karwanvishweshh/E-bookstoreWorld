package com.bookstore.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bookstore.entities.Book;
import com.bookstore.entities.Customer;
import com.bookstore.exceptions.BooksIdNotFoundException;
import com.bookstore.repositories.BookRepository;
import com.bookstore.service.BooksService;


@Service
public class BooksServiceImpl implements BooksService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);

	@Autowired
	private BookRepository booksRepository;

	@Override
	public Book createdOrUpdatebook(Book booksModule) {

		
		if (booksModule.getId() == null) {
            logger.info("Adding new Book: {}", booksModule.getId());
            Book bookmod = booksRepository.save(booksModule);
            logger.info("Book added with ID: {}", bookmod.getId());
            return bookmod;
        } else {
            logger.info("Updating Book with ID: {}", booksModule.getId());
            Optional<Book> getById = booksRepository.findById(booksModule.getId());

            if (getById.isPresent()) {
                Book existBook = getById.get();
                existBook.setTitle(booksModule.getTitle());
                existBook.setPrice(booksModule.getPrice());
                Book updatedBook = booksRepository.save(existBook);
                logger.info("Book updated successfully with ID: {}", updatedBook.getId());
                return updatedBook;
            } else {
                logger.warn("Book ID not found: {}", booksModule.getId());
                throw new BooksIdNotFoundException("Book ID not found");
            }
        }

	}

	@Override
	@Cacheable(cacheNames = "booksmodule" ,key ="#bookId")
	public Book getBookId(Long bookId) {
		System.err.println("getbyBookId  calls from database ...............................");
		Optional<Book> bId = booksRepository.findById(bookId);

		if (!bId.isPresent()) {

			throw new BooksIdNotFoundException("Book id notfound");

		}

		return bId.get();
	}

	@Override
    @Cacheable(value = "allbooks")
	public List<Book>  getByAllBookss() {
		System.err.println("getbyAll books calls from database ...............................");
		List<Book> list = booksRepository.findAll();
		
		return list;
		
	}

}
