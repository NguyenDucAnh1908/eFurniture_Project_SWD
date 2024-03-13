
package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UpdateUserResponse.UpdateUserResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.AdditionalInfoDto;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.UserStatsDTO;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
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

    List<User> findAllUser();

    UserResponse getUserById(Long id);

    ResponseEntity<ObjectResponse> deleteUser(Long userId);

    ResponseEntity<UpdateUserResponse> updateUser(Long userId, UserDto updateUserRequest);

    UserStatsDTO getUserStats();

    void receiveAndConfirmConsultation(Long id, AdditionalInfoDto additionalInfoDto) throws DataNotFoundException;

    void cancelBooking(Long bookingId) throws DataNotFoundException;
    Page<UserResponse> getAllUsers(PageRequest pageRequest, Role role);

}



