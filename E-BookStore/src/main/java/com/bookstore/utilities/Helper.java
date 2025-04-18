package com.bookstore.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.BooksExcelFile;

public class Helper {
	
	public static boolean checkExcel(MultipartFile file) {
		
		return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		
		
	}
	
	public static List<BooksExcelFile> convertExcelFileintoDB(InputStream in) throws IOException{
		
		List<BooksExcelFile> list= new ArrayList<>();
		
		Set<String> uniquProducts= new HashSet<>();
		
		//Step:1  Read the Excel Sheet
		
		XSSFWorkbook work= new XSSFWorkbook(in);
		
		//Step:2 Access the Sheet
		
		XSSFSheet sheet= work.getSheetAt(0);
		
		//Step:3 IteratorsRows
		
		Iterator<Row> iteratorRows=sheet.iterator();
		
		//step:4 skip the header
		
		if(iteratorRows.hasNext()) {
			iteratorRows.next();
		}
		
		//step:5 read the data access rows
		
		while(iteratorRows.hasNext()) {
			Row row=iteratorRows.next();
			
			Iterator<Cell> cells= row.iterator();
			
			BooksExcelFile excelFile= new BooksExcelFile();
			
			while(cells.hasNext()) {
				Cell cell=cells.next();
				
				switch (cell.getColumnIndex()) {
				case 0:
					excelFile.setProduct(cell.getStringCellValue());
					break;
				case 1:
					excelFile.setDescription(cell.getStringCellValue());
					break;
				case 2:
					excelFile.setPrice(cell.getNumericCellValue());
					break;
				default:
					break;
				}
				
				if(uniquProducts.add(excelFile.getProduct())) {
					list.add(excelFile);
				}
			}
		}
		return list;
		
	}

}
