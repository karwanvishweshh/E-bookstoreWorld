package com.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.File;

@Repository
public interface FileUploadRepository extends JpaRepository<File,Long>{

}
