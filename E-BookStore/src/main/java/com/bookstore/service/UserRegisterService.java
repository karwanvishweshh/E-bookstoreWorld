package com.bookstore.service;

import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;
import com.bookstore.model.UserRegisterDTO;

public interface UserRegisterService {

  UserRegister createUserRegService(UserRegisterDTO userRegisterDTO, MultipartFile file);

UserRegister createLoginUser(LoginModel loginModel);

}
