package com.bookstore.service;

import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;
import com.bookstore.model.UserRegisterDTO;

public interface UserRegisterService {

  UserRegister createUserRegService(UserRegisterDTO userRegisterDTO);

UserRegister createLoginUser(LoginModel loginModel);

}
