package com.bookstore.api;

import com.bookstore.service.BooksExcelDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksExcelDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(BooksExcelDownloadController.class);

    @Autowired
    private  BooksExcelDownloadService booksExcelDownloadService;

   
    @GetMapping("/downloadBookDetails")
    public ResponseEntity<String> downloadBookDetails() {
        logger.info("Download book details request received.");

        ResponseEntity<String> response = booksExcelDownloadService.generateBookExcel();

        return response;
    }
}
