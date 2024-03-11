package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UpdateUserReponse.UpdateUserResponse;
import com.eFurnitureproject.eFurniture.Responses.UserListResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.UserStatsDTO;
import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IUserService {
    ResponseEntity<ObjectResponse> createUser(UserDto request);

    AuthenticationResponse authenticate(AuthenticationDTO request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User findByEmailForMail(String email);

    User saveUserForMail(User user);

//    List<User> findAllUser(int page, Role role);

    UserResponse getUserById(Long userId);

    ResponseEntity<ObjectResponse> deleteUser(Long userId);

    ResponseEntity<UpdateUserResponse> updateUser(Long userId, UserDto updateUserRequest);
    UserStatsDTO getUserStats();

//    Page<UserResponse> getAllUsers(PageRequest pageRequest, Role role);

//    ResponseEntity<UserListResponse> findAllUsers();

    List<User> getAllUser();

    Page<UserResponse> getAllUsers(PageRequest pageRequest, Role role);
}
