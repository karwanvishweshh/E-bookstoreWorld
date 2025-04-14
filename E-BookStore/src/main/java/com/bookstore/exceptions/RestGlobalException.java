package com.bookstore.exceptions;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookstore.model.ErrorResponse;
import com.bookstore.utilities.Constants;


@RestControllerAdvice
public class RestGlobalException {

	@ExceptionHandler(CustomerIdNotFoundException.class)
	public ResponseEntity<Object> handleCustmerExcpetions(CustomerIdNotFoundException ex) {
		List<String> details = new ArrayList<>();
		details.add("Error : Custmer ID not found");
		details.add("Detailed Message" + ex.getLocalizedMessage());
		details.add("Timestamp:" + System.currentTimeMillis());
		ErrorResponse er = new ErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "NOTEXIST", details);
		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	

	}
	
	@ExceptionHandler(BooksIdNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBooksIdNotFound(BooksIdNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Book Not Found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
	
	
	
	
	
	
	
	
	
	
	

}
