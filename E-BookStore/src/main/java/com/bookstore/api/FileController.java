package com.bookstore.api;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.File;
import com.bookstore.model.ResponseMessage;
import com.bookstore.service.UploadService;
import com.bookstore.utilities.Constants;

@RestController
public class FileController {
	
	@Autowired
	private UploadService uploadService;
	
	
	@PostMapping("/uploadFile")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file)
	{
		try {
			if(file==null) {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "File cannot be empty"));
			}
			
		return uploadService.upload(file);
		
		}catch(Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED,"Upload Fails"));
		}

		
	
	}
	
	@PostMapping("/uploadFiles")
	public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam MultipartFile[] files)
	{
		
		try {
			if(files==null) {
				return ResponseEntity.ok( new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "File cannot be empty"));
			}
			
		return uploadService.uploads(files);
		
		}catch(Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED,"Upload Fails"));
		}
		
		
	}
	
	

}
