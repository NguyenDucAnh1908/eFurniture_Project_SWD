package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IUserService {
    ResponseEntity<ObjectResponse> createUser(UserDto request);

    AuthenticationResponse authenticate(AuthenticationDTO request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User findByEmailForMail(String email);

    User saveUserForMail(User user);

    User findAllUser();

    HttpEntity<Object> updateUser(String email, UserDto updateUserRequest);
}
