package com.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.BooksExcelFile;

public interface BooksExcelFileRepository extends JpaRepository<BooksExcelFile,Long>{

}
