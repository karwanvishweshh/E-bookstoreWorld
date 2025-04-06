package com.bookstore.api;

import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;
import com.bookstore.model.ResponseMessage;
import com.bookstore.model.UserRegisterDTO;
import com.bookstore.service.UserRegisterService;
import com.bookstore.utilities.Constants;

@RestController
public class UserRegisterAPI {
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	
	
	@PostMapping("/userregister")
	public ResponseEntity<ResponseMessage> createUserRegistration(@RequestBody UserRegisterDTO userRegisterDTO)
	{
		
	 try 
	 {
			 if(userRegisterDTO ==null || userRegisterDTO.getEmail()==null || userRegisterDTO.getEmail().isBlank() || userRegisterDTO.getPassword() ==null || userRegisterDTO.getPassword().isBlank())
             {
 		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and password cannot be empty!"));
 		}
      UserRegister userRegService = userRegisterService.createUserRegService(userRegisterDTO);
		if(userRegService!=null)
		{

		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Register Saved sucessfully", userRegService));

	  }
	  else 
	  {
		  
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "User Register not Saved sucessfully", userRegService));
	  }
	 }catch (Exception e) 
	 {
		
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "User Registration failed"));
	}
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage> createLogin(@RequestBody LoginModel loginModel) 
	{
		
		// handle to the exception with try and catch
	try {	
		// Handling email and password empty you need pass below message
     if(loginModel ==null || loginModel.getEmail()==null || loginModel.getEmail().isBlank() || loginModel.getPassword() ==null || loginModel.getPassword().isBlank()){
    	 
 		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and password cannot be empty!"));
 		
	}
    // create the service class
      UserRegister userLogin = userRegisterService.createLoginUser(loginModel);
      
      // check the conditions user exist or not
      if(userLogin!=null) {

  		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Login sucessfully, Welcome to E-commerce online Book store..!!", userLogin));

  	  }else {
  		  
  		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Invalid email and password"));
  	}}catch (Exception e) {
		
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Bad Credintials.."));

	}
  	}
	
	
}
