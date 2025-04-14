package com.bookstore.serviceImpl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	
	
	private static final String DOWNLOAD_DIR = "C:\\DownloadsNew";

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




	@Override
	public ResponseEntity<ResponseMessage> deleteById(long id) {
		fileRepo.deleteById(id);
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,Constants.SUCCESS,"File Deleted Successfully"));
	}




	@Override
	public ResponseEntity<ResponseMessage> getFileById(long id) {
		File fis=fileRepo.getById(id);
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,Constants.SUCCESS,"File Reterieved Successfully"+fis));
	}




	@Override
    public ResponseEntity<ResponseMessage> downloadToLocalDisk(long id) {
        try {
            File dbFile = fileRepo.findById(id).orElse(null);
            if (dbFile == null) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_NOT_FOUND,
                        Constants.FAILED, "File not found with ID: " + id));
            }

            Path dirPath = Paths.get(DOWNLOAD_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(dbFile.getFileName());

            Files.write(filePath, dbFile.getData());

            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,
                    Constants.SUCCESS, "File Downloaded to Local Disk: " + filePath.toString()));

        } catch (IOException e) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR,
                    Constants.FAILED, "Failed to download file to disk: " + e.getMessage()));
        }
    
	}
}
	
