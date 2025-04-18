package com.bookstore.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import com.bookstore.model.ResponseMessage;

public interface BooksExcelDownloadService {

	ResponseEntity<String> generateBookExcel();

}
