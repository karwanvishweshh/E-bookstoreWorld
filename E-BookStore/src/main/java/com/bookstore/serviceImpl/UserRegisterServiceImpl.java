package com.bookstore.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserRegisterServiceImpl implements UserRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    @Autowired
    private UserRegisterRepository userRegisterRepo;

    @Autowired
    private FileUploadRepository fileRepo;

    @Override
    public UserRegister createUserRegService(UserRegisterDTO userRegisterDTO, MultipartFile file) {
        logger.info("Creating new user registration for email: {}", userRegisterDTO.getEmail());
        UserRegister user = null;

        try {
            user = new UserRegister();
            user.setFirstName(userRegisterDTO.getFirstName());
            user.setLastName(userRegisterDTO.getLastName());
            user.setEmail(userRegisterDTO.getEmail());
            user.setPassword(Base64.getEncoder().encodeToString(userRegisterDTO.getPassword().getBytes()));
            user.setContactId(userRegisterDTO.getContactId());

            if (file != null && !file.isEmpty()) {
                logger.info("Image file received for user: {}", userRegisterDTO.getEmail());
                user.setImage(file.getBytes());
            }

            userRegisterRepo.save(user);
            logger.info("User successfully registered with email: {}", user.getEmail());

        } catch (Exception e) {
            logger.error("Exception occurred while registering user: {}", e.getMessage(), e);
        }

        return user;
    }

    @Override
    public UserRegister createLoginUser(LoginModel loginModel, MultipartFile[] files) throws IOException {
        logger.info("Attempting login for email: {}", loginModel.getEmail());

        UserRegister user = userRegisterRepo.findByEmail(loginModel.getEmail());

        if (user != null) {
            logger.debug("User found in database for email: {}", loginModel.getEmail());
            String decodedPassword = new String(Base64.getDecoder().decode(user.getPassword()));

            if (decodedPassword.equals(loginModel.getPassword())) {
                logger.info("Password matched for user: {}", loginModel.getEmail());

                if (files != null && files.length > 0) {
                    logger.info("Uploading {} files for user: {}", files.length, loginModel.getEmail());

                    for (MultipartFile multipartFile : files) {
                        File fileEntity = new File();
                        fileEntity.setFileType(multipartFile.getContentType());
                        fileEntity.setFileName(multipartFile.getOriginalFilename());
                        fileEntity.setData(multipartFile.getBytes());
                        fileRepo.save(fileEntity);
                        logger.debug("File saved: {}", multipartFile.getOriginalFilename());
                    }
                } else {
                    logger.info("No files uploaded during login for user: {}", loginModel.getEmail());
                }

                return user;
            } else {
                logger.warn("Incorrect password for user: {}", loginModel.getEmail());
            }
        } else {
            logger.warn("User not found with email: {}", loginModel.getEmail());
        }

        return null;
    }
}
