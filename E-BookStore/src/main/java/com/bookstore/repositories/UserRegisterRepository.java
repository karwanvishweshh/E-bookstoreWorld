package com.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.UserRegister;
import com.bookstore.model.LoginModel;

@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegister,Long> {

	UserRegister findByEmail(String email);

}
