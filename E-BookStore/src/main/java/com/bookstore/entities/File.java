package com.bookstore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name="filename")
	private String fileName;
	@Column(name="filetype")
	private String fileType;
	@Lob
	@Column(columnDefinition="longblob")
	private byte[] data;
}
