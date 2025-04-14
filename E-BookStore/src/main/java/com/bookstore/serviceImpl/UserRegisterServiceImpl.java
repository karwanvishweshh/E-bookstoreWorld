package com.bookstore.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.File;
import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;
import com.bookstore.model.UserRegisterDTO;
import com.bookstore.repositories.FileUploadRepository;
import com.bookstore.repositories.UserRegisterRepository;
import com.bookstore.service.UserRegisterService;




@Service
public class UserRegisterServiceImpl  implements UserRegisterService{
	
	@Autowired
	private UserRegisterRepository userRegisterRepo;

	@Autowired
	private FileUploadRepository fileRepo;

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
	public UserRegister createLoginUser(LoginModel loginModel, MultipartFile[] files) throws IOException {
	    UserRegister user = userRegisterRepo.findByEmail(loginModel.getEmail());

	    if (user != null) {
	        String decodedPassword = new String(Base64.getDecoder().decode(user.getPassword()));

	        if (decodedPassword.equals(loginModel.getPassword())) {
	            if (files != null && files.length > 0) {
	                for (MultipartFile multipartFile : files) {
	                    File fileEntity = new File();
	                    fileEntity.setFileType(multipartFile.getContentType());
	                    fileEntity.setFileName(multipartFile.getOriginalFilename());
	                    fileEntity.setData(multipartFile.getBytes());
	                    fileRepo.save(fileEntity);
	                }
	            }
	            return user;
	        }
	    }

	    return null;
	}
	
	

}