package com.bookstore.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BooksExcelFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="productname")
	private String product;
	@Column(name="description")
	private String description;
	
	@Column(name="price")
	private Double price;
	
	@CreationTimestamp
	@Column(name="createdate")
	private LocalDateTime createDate;
	
	@UpdateTimestamp
	@Column(name="updateDate")
	private LocalDateTime updateDate;
	

}
