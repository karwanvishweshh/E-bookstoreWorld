package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.ResponseMessage;



public interface UploadService {

	ResponseEntity<ResponseMessage> upload(MultipartFile file) throws IOException;

	ResponseEntity<ResponseMessage> uploads(MultipartFile[] files);

}
