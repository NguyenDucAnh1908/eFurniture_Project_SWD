package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.UserStatsDTO;
import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.models.Enum.TokenType;
import com.eFurnitureproject.eFurniture.models.Token;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.TokenRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern = Pattern.compile(emailRegex);

    @Override
    public ResponseEntity<ObjectResponse> createUser(UserDto request) {
        if (!(request.getPhoneNumber().length() == 10)) {
            throw new RuntimeException("Phone is not value");
        }
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            throw new RuntimeException("Email is not value");
        }
        var user = User.builder()
                .email(request.getEmail())
                .active(true)
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .role(Role.USER)
                .build();
        var existedEmail = repository.findByEmail(user.getEmail()).orElse(null);
        if (existedEmail == null) {
            repository.save(user);
            return ResponseEntity.ok().body(ObjectResponse.builder()
                    .status("Success")
                    .message("Register success")
                    .userResponse(convertToUserResponse(user))
                    .build());
        } else {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Register fail")
                    .message("Account existed")
                    .build());
        }
    }

    private UserResponse convertToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .active(user.isActive())
                .role(user.getRole())
                .build();
    }



    private List<UserResponse> convertList(List<User> userList) {
        List<UserResponse> userResponseList = new ArrayList<>();

        for (User user : userList) {
            UserResponse userResponse = convertToUserResponse(user);
            userResponseList.add(userResponse);
        }

        return userResponseList;
    }



    @Override
    public AuthenticationResponse authenticate(AuthenticationDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefeshToken(user);
        revokeAllUsserTokens(user);
        saveToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .staus("Success")
                .messages("Login success")
                .token(jwtToken)
                .user(convertToUserResponse(user))
                .refeshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var newToken = jwtService.generateToken(user);
                revokeAllUsserTokens(user);
                saveToken(user, newToken);
                var authResponse = AuthenticationResponse.builder()
                        .staus("Success")
                        .messages("New token")
                        .token(newToken)
                        .refeshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public User findByEmailForMail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUserForMail(User user) {
        return repository.save(user);
    }

    @Override
    public List<UserResponse> findAllUser() {
        try {
            List<User> userList =repository.findAll();
            return convertList(userList);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ObjectResponse> deleteUser(String email) {
        var user = repository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setActive(false);
            repository.save(user);
            return ResponseEntity.ok().body(new ObjectResponse("Success","User deleted successfully",convertToUserResponse(user)));
        } else {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Fail")
                    .message("User not found")
                    .build());
        }
    }

    @Override
    public ResponseEntity<ObjectResponse> updateUser(String email, UserDto updateUserRequest) {
        var user = repository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Fail")
                    .message("User not found")
                    .userResponse(null)
                    .build());
        }
        if (updateUserRequest != null && updateUserRequest.getFullName() != null && !updateUserRequest.getFullName().isEmpty()) {
            user.setFullName(updateUserRequest.getFullName());
        }
        Matcher matcher = pattern.matcher(updateUserRequest.getEmail());
        if (matcher.matches()) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if ((updateUserRequest.getPhoneNumber() != null && updateUserRequest.getPhoneNumber().length() == 10)) {
            user.setPhoneNumber(user.getPhoneNumber());
        }
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            user.setPassword(updateUserRequest.getPassword());
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Fail")
                    .message("ServletRequestAttributes not found")
                    .userResponse(null)
                    .build());
        }

        user.setActive(updateUserRequest.isActive());
        return ResponseEntity.ok(ObjectResponse.builder()
                .status("Success")
                .message("Update User Success")
                .userResponse(convertToUserResponse(user))
                .build());
    }

    private void saveToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUsserTokens (User user){
        var vaildUserToken = tokenRepository.findAllVaildTokenByUser(user.getId());
        if (vaildUserToken.isEmpty())
            return;
        vaildUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
    }


    public UserStatsDTO getUserStats() {
        Long usersThisMonth = repository.countUsersThisMonth();
        Long usersLastMonth = repository.countUsersLastMonth();

        double percentageChange;
        if (usersLastMonth == 0) {
            percentageChange = usersThisMonth > 0 ? 100 : 0;
        } else {
            percentageChange = ((double) (usersThisMonth - usersLastMonth) / usersLastMonth) * 100;
        }

        return new UserStatsDTO(usersThisMonth, usersLastMonth, percentageChange);
    }

}







