package com.bookstore.model;


import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {


	private Integer statuscode;
	private String status;
	private String message;
	private List<?> response;

	public ErrorResponse(Integer statuscode, String status, String message, List<?> response) {
		super();
		this.statuscode = statuscode;
		this.status = status;
		this.message = message;
		this.response = response;
	}
	
}
