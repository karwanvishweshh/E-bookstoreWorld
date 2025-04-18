package com.bookstore.api;

import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.ResponseMessage;
import com.bookstore.service.UploadService;
import com.bookstore.utilities.Constants;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file) {
        logger.info("Single file upload initiated");

        try {
            if (file == null || file.isEmpty()) {
                logger.warn("Upload failed: file is null or empty");
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "File cannot be empty"));
            }

            logger.info("Uploading file: {}", file.getOriginalFilename());
            return uploadService.upload(file);

        } catch (Exception e) {
            logger.error("Exception occurred while uploading file", e);
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Upload Failed"));
        }
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam MultipartFile[] files) {
        logger.info("Multiple file upload initiated");

        try {
            if (files == null || files.length == 0) {
                logger.warn("Upload failed: no files provided");
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "File cannot be empty"));
            }

            logger.info("Uploading {} files", files.length);
            return uploadService.uploads(files);

        } catch (Exception e) {
            logger.error("Exception occurred while uploading multiple files", e);
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Upload Failed"));
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseMessage> getFile(@PathVariable long id) {
        logger.info("Fetching file with ID: {}", id);
        return uploadService.getFileById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable long id) {
        logger.info("Deleting file with ID: {}", id);
        return uploadService.deleteById(id);
    }

    @GetMapping("/downloadToDisk/{id}")
    public ResponseEntity<ResponseMessage> downloadToDisk(@PathVariable long id) {
        logger.info("Downloading file with ID {} to local disk", id);
        return uploadService.downloadToLocalDisk(id);
    }
}
