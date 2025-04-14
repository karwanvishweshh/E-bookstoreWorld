package com.bookstore.api;

import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;
import com.bookstore.model.ResponseMessage;
import com.bookstore.model.UserRegisterDTO;
import com.bookstore.service.UserRegisterService;
import com.bookstore.utilities.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
@Api(value="User Registration and Login Operations",tags= {"User operations"})
public class UserRegisterAPI {
	
	private final static Logger logger=LoggerFactory.getLogger(UserRegisterAPI.class);
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	@ApiOperation(value="User Registration",notes="Register a new user",response=ResponseMessage.class)
	@ApiResponses(value= {
			@ApiResponse(code=201,message="User Registered Successfully",response=ResponseMessage.class),
			@ApiResponse(code=400,message="User Registered Failed",response=ResponseMessage.class),
			@ApiResponse(code=500,message="Internal Server Error",response=ResponseMessage.class),
			
	})
	@PostMapping(value="/userregister")
	public ResponseEntity<ResponseMessage> createUserRegistration(@RequestBody @ModelAttribute UserRegisterDTO userRegisterDTO,@RequestParam MultipartFile file)
	{
		
	 try 
	 {
		 logger.info(" User Register API method call Started");
			 if(userRegisterDTO ==null || userRegisterDTO.getEmail()==null || userRegisterDTO.getEmail().isBlank() || userRegisterDTO.getPassword() ==null || userRegisterDTO.getPassword().isBlank()||file==null)
             {
 		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and password cannot be empty!"));
 		}
      UserRegister userRegService = userRegisterService.createUserRegService(userRegisterDTO,file);
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
		logger.error("User register Failed");
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "User Registration failed"));
	}
	}
	
	@ApiOperation(value="User Login",notes="User Login To portal",response=ResponseMessage.class)
	@ApiResponses(value= {
			@ApiResponse(code=201,message="User Logged Successfully",response=ResponseMessage.class),
			@ApiResponse(code=400,message="User Login Failed",response=ResponseMessage.class),
			@ApiResponse(code=500,message="Internal Server Error",response=ResponseMessage.class),
			
	})
	
	
//	@PostMapping("/login")
//	public ResponseEntity<ResponseMessage> createLogin(@RequestBody LoginModel loginModel) 
//	{
//		
//		// handle to the exception with try and catch
//	try {	
//		// Handling email and password empty you need pass below message
//     if(loginModel ==null || loginModel.getEmail()==null || loginModel.getEmail().isBlank() || loginModel.getPassword() ==null || loginModel.getPassword().isBlank()){
//    	 
// 		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and password cannot be empty!"));
// 		
//	}
//    // create the service class
//      UserRegister userLogin = userRegisterService.createLoginUser(loginModel);
//      
//      // check the conditions user exist or not
//      if(userLogin!=null) {
//
//  		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Login sucessfully, Welcome to E-commerce online Book store..!!", userLogin));
//
//  	  }else {
//  		  
//  		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Invalid email and password"));
//  	}}catch (Exception e) {
//		
//		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Bad Credintials.."));
//
//	}
//  	}
//	
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage> createLogin(@RequestParam String loginDataJson,@RequestParam MultipartFile[] files) 
	{
		
	try {	
		logger.info(" User Login API method call Started");
		
    LoginModel loginData= new ObjectMapper().readValue(loginDataJson,LoginModel.class);
      UserRegister userRegService= userRegisterService.createLoginUser(loginData,files);
	
      if(userRegService!=null) {
  		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Login sucessfully, Welcome to E-commerce online Book store..!!", userRegService));
  	  }else {  
  		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Invalid email and password"));
  	}}catch (Exception e) {
		
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Bad Credintials.."));

	}
  	}

}
