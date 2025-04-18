package com.bookstore.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.ResponseMessage;

public interface UploadBooksExcelService {

	void uploadFilesFromExcel(MultipartFile file) throws IOException;

	

	

}
