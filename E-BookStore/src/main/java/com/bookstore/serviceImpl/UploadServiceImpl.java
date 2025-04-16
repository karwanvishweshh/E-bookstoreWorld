package com.bookstore.serviceImpl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    private static final String DOWNLOAD_DIR = "C:\\DownloadsNew";

    @Autowired
    private FileUploadRepository fileRepo;

    @Override
    public ResponseEntity<ResponseMessage> upload(MultipartFile file) throws IOException {
        logger.info("Uploading single file: {}", file.getOriginalFilename());

        File fis = new File();
        fis.setFileType(file.getContentType());
        fis.setFileName(file.getOriginalFilename());
        fis.setData(file.getBytes());

        fileRepo.save(fis);

        logger.info("File uploaded successfully: {}", file.getOriginalFilename());
        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_ACCEPTED, Constants.SUCCESS,
                "File Uploaded Successfully: " + file.getOriginalFilename()));
    }

    @Override
    public ResponseEntity<ResponseMessage> uploads(MultipartFile[] files) {
        logger.info("Uploading {} files", files.length);

        List<String> response = Arrays.stream(files)
            .map(file -> {
                try {
                    File fs = new File();
                    fs.setFileName(file.getOriginalFilename());
                    fs.setFileType(file.getContentType());
                    fs.setData(file.getBytes());
                    fileRepo.save(fs);
                    logger.info("Uploaded: {}", file.getOriginalFilename());
                    return "Uploaded: " + file.getOriginalFilename();
                } catch (IOException e) {
                    logger.error("Failed to upload file: {}", file.getOriginalFilename(), e);
                    return "Failed: " + file.getOriginalFilename();
                }
            })
            .collect(Collectors.toList());

        logger.info("Bulk upload process completed");
        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
                "Files Uploaded Successfully", response));
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteById(long id) {
        logger.info("Deleting file with ID: {}", id);
        fileRepo.deleteById(id);
        logger.info("File deleted successfully with ID: {}", id);
        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
                "File Deleted Successfully"));
    }

    @Override
    public ResponseEntity<ResponseMessage> getFileById(long id) {
        logger.info("Fetching file by ID: {}", id);
        File fis = fileRepo.getById(id);
        logger.info("File retrieved: {}", fis.getFileName());
        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
                "File Retrieved Successfully", fis));
    }

    @Override
    public ResponseEntity<ResponseMessage> downloadToLocalDisk(long id) {
        logger.info("Downloading file with ID: {} to local disk", id);

        try {
            File dbFile = fileRepo.findById(id).orElse(null);
            if (dbFile == null) {
                logger.warn("File not found for ID: {}", id);
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_NOT_FOUND,
                        Constants.FAILED, "File not found with ID: " + id));
            }

            Path dirPath = Paths.get(DOWNLOAD_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                logger.info("Download directory created at: {}", dirPath.toString());
            }

            Path filePath = dirPath.resolve(dbFile.getFileName());
            Files.write(filePath, dbFile.getData());

            logger.info("File downloaded successfully to: {}", filePath.toString());
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK,
                    Constants.SUCCESS, "File Downloaded to Local Disk: " + filePath.toString()));

        } catch (IOException e) {
            logger.error("Error while downloading file to disk: {}", e.getMessage(), e);
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR,
                    Constants.FAILED, "Failed to download file to disk: " + e.getMessage()));
        }
    }
}
