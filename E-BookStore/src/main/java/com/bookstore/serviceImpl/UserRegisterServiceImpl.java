package com.bookstore.serviceImpl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;
import com.bookstore.model.UserRegisterDTO;
import com.bookstore.repositories.UserRegisterRepository;
import com.bookstore.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl  implements UserRegisterService{
	
	@Autowired
	private UserRegisterRepository userRegisterRepo;

	@Override
	public UserRegister createUserRegService(UserRegisterDTO userRegisterDTO,MultipartFile file) {
		UserRegister  user =null;
		try {
		user = new UserRegister();
		user.setFirstName(userRegisterDTO.getFirstName());
		user.setLastName(userRegisterDTO.getLastName());
		user.setEmail(userRegisterDTO.getEmail());
		user.setPassword(Base64.getEncoder().encodeToString(userRegisterDTO.getPassword().getBytes()));
		user.setContactId(userRegisterDTO.getContactId());
		if(file !=null) {
			user.setImage(file.getBytes());
		}
		userRegisterRepo.save(user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public UserRegister createLoginUser(LoginModel loginModel) {
	UserRegister userEmail=	userRegisterRepo.findByEmail(loginModel.getEmail());
	
	if(userEmail !=null) {
		
		
		String decode=new String(Base64.getDecoder().decode(userEmail.getPassword()));
		
		if(decode.equals(loginModel.getPassword())) {
			
			return userEmail;
		}
	}
		
		return null;
	}

}
