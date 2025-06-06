package com.bookstore.model;

import org.springframework.web.multipart.MultipartFile;

public class UserRegisterDTO {
	
	private String firstName; 
	private String lastName;
	private String email;
	private String password;
	private long contactId;
	
	private MultipartFile file;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public MultipartFile getFile() {
	    return file;
	}

	
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
	
	
	
	

}
