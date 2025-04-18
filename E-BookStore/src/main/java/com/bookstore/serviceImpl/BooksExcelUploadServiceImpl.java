package com.bookstore.serviceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.BooksExcelFile;
import com.bookstore.model.ResponseMessage;
import com.bookstore.repositories.BooksExcelFileRepository;
import com.bookstore.service.UploadBooksExcelService;
import com.bookstore.utilities.Constants;
import com.bookstore.utilities.Helper;

@Service
public class BooksExcelUploadServiceImpl implements UploadBooksExcelService {

    private static final Logger logger = LoggerFactory.getLogger(BooksExcelUploadServiceImpl.class);
   
    
    @Autowired
    private BooksExcelFileRepository booksExcelFileRepository;

    @Override
    public void uploadFilesFromExcel(MultipartFile file) throws IOException {
        logger.info("Starting to process Excel file: {}", file.getOriginalFilename());

        try {
            List<BooksExcelFile> convertExcelFileintoDB = Helper.convertExcelFileintoDB(file.getInputStream());
            logger.info("Parsed {} entries from Excel file: {}", convertExcelFileintoDB.size(), file.getOriginalFilename());

            booksExcelFileRepository.saveAll(convertExcelFileintoDB);
            logger.info("Saved all entries to database from file: {}", file.getOriginalFilename());

        } catch (IOException e) {
            logger.error("IOException while processing Excel file: {}", file.getOriginalFilename(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while processing Excel file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Error processing Excel file", e);
        }
    }

   
}
    
    
