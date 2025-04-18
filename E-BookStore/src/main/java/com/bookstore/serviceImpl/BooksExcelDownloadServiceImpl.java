package com.bookstore.serviceImpl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookstore.entities.Book;
import com.bookstore.repositories.BookRepository;
import com.bookstore.service.BooksExcelDownloadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class BooksExcelDownloadServiceImpl implements BooksExcelDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(BooksExcelDownloadServiceImpl.class);

    private final BookRepository bookRepository;

    // Path where the file will be saved
    private static final String FILE_PATH = "C:\\DownloadsNew\\BookDetails.xlsx";

    public BooksExcelDownloadServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponseEntity<String> generateBookExcel() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            // Create a new sheet
            Sheet sheet = workbook.createSheet("Books");

            // Create the header row
            Row headerRow = sheet.createRow(0);
            String[] columns = { "Book ID", "Title", "Description", "Price" };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);

                // Style for header
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Fetch all books from the database
            List<Book> books = bookRepository.findAll();
            if (books.isEmpty()) {
                logger.warn("No books found in the database.");
            }

            // Populate data rows
            int rowIdx = 1;
            for (Book book : books) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(book.getId());
                row.createCell(1).setCellValue(book.getTitle());
                row.createCell(2).setCellValue(book.getDescription());
                row.createCell(3).setCellValue(book.getPrice());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the workbook to local disk
            File outputFile = new File(FILE_PATH);
            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
                workbook.write(fileOut);
            }

            logger.info("Excel file with book details saved successfully at: {}", FILE_PATH);
            return ResponseEntity.ok("Excel file saved successfully at: " + FILE_PATH);

        } catch (IOException e) {
            logger.error("Error generating or saving the Excel file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to generate Excel file: " + e.getMessage());
        }
    }
}
