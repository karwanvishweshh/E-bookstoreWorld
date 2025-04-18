package com.bookstore.api;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.ResponseMessage;
import com.bookstore.service.UploadBooksExcelService;
import com.bookstore.utilities.Constants;
import com.bookstore.utilities.Helper;

@RestController
public class BooksExcelUploadController {

	private static final Logger logger = LoggerFactory.getLogger(BooksExcelUploadController.class);

	@Autowired
	private UploadBooksExcelService uploadBooksExcelService;

	@PostMapping("/uploadExcel")
	public ResponseEntity<ResponseMessage> uploadExcelBooks(@RequestParam MultipartFile file) throws IOException {
		logger.info("Received request to upload Excel file: {}", file.getOriginalFilename());

		if (Helper.checkExcel(file)) {
			logger.info("File validation passed for: {}", file.getOriginalFilename());

			try {
				uploadBooksExcelService.uploadFilesFromExcel(file);
				logger.info("Excel file processed and uploaded successfully: {}", file.getOriginalFilename());

				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS,
						"Files uploaded successfully"));

			} catch (Exception e) {
				logger.error("Error while processing the Excel file: {}", file.getOriginalFilename(), e);

				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED,
						"Failed to process Excel file", file.getOriginalFilename(), null));
			}

		} else {
			logger.warn("Invalid file format received: {}", file.getOriginalFilename());

			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED,
					"Please upload a valid Excel file!", file.getOriginalFilename(), null));
		}
	}
}
