package com.bookstore.api;

import java.net.HttpURLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.Book;
import com.bookstore.model.ResponseMessage;
import com.bookstore.service.BooksService;
import com.bookstore.utilities.Constants;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BooksService booksModuleService;

    @PostMapping("/createorupdatebooks")
    public ResponseEntity<ResponseMessage> booksCreated(@ApiParam(value = "User BooksModule Data", required = true) @RequestBody Book booksModule) {
        logger.info("booksCreated API called");

        try {
            if (booksModule == null || booksModule.getTitle() == null || booksModule.getTitle().isBlank()) {
                logger.warn("Book title or price missing in request");
                logger.debug("Received booksModule: {}", booksModule);
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Title and price cannot be empty!"));
            }

            logger.info("Processing createOrUpdate for book: {}", booksModule.getTitle());
            Book createdOrUpdatedBook = booksModuleService.createdOrUpdatebook(booksModule);

            if (createdOrUpdatedBook != null) {
                logger.info("Book created/updated successfully: ID={}", createdOrUpdatedBook.getId());
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Book created successfully", createdOrUpdatedBook));
            } else {
                logger.warn("Book creation/update failed in service layer");
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book creation failed"));
            }
        } catch (Exception e) {
            logger.error("Exception while creating/updating book: {}", e.getMessage(), e);
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book creation failed"));
        }
    }

    @GetMapping("/getByBookId/{bookId}")
    public ResponseEntity<ResponseMessage> getByBooksId(@PathVariable Long bookId) {
        logger.info("Fetching book by ID: {}", bookId);

        try {
            if (bookId == null || bookId == 0) {
                logger.warn("Invalid bookId provided");
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book ID cannot be empty!"));
            }

            Book book = booksModuleService.getBookId(bookId);

            if (book != null) {
                logger.info("Book retrieved successfully: {}", book.getTitle());
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Book ID successfully retrieved", book));
            } else {
                logger.warn("No book found for ID: {}", bookId);
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book ID not found"));
            }
        } catch (Exception e) {
            logger.error("Error while retrieving book by ID {}: {}", bookId, e.getMessage(), e);
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book ID retrieval failed"));
        }
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<ResponseMessage> getByAllBooks() {
        logger.info("Fetching all books");

        try {
            List<Book> booksList = booksModuleService.getByAllBookss();

            if (booksList != null && !booksList.isEmpty()) {
                logger.info("Total books retrieved: {}", booksList.size());
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "All books retrieved successfully", booksList));
            } else {
                logger.warn("No books found in the system");
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "No books found"));
            }
        } catch (Exception e) {
            logger.error("Error while fetching all books: {}", e.getMessage(), e);
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Fetching all books failed"));
        }
    }
}

