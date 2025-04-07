package com.bookstore.serviceImpl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.File;
import com.bookstore.model.ResponseMessage;
import com.bookstore.repositories.FileUploadRepository;
import com.bookstore.service.UploadService;
import com.bookstore.utilities.Constants;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private FileUploadRepository fileRepo;

	@Override
	public ResponseEntity<ResponseMessage> upload(MultipartFile file) throws IOException {
		File fis = new File();
		fis.setFileType(file.getContentType());
		fis.setFileName(file.getOriginalFilename());
		fis.setData(file.getBytes());
		fileRepo.save(fis);
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_ACCEPTED , Constants.SUCCESS,"File Uploaded Successfully"+ file.getOriginalFilename()));
		
	}
	
	
	

	@Override
	public ResponseEntity<ResponseMessage> uploads(MultipartFile[] files) {
	    List<String> response = Arrays.stream(files)
	        .map(file -> {
	            try {
	                File fs = new File();
	                fs.setFileName(file.getOriginalFilename());
	                fs.setFileType(file.getContentType());
	                fs.setData(file.getBytes());
	                fileRepo.save(fs);
	                return "Uploaded: " + file.getOriginalFilename();
	            } catch (IOException e) {
	                return "Failed: " + file.getOriginalFilename();
	            }
	        })
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,Constants.SUCCESS,"Files Uploaded Successfully",response));
	}
}
