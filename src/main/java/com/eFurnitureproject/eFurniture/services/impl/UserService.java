package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UpdateUserResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
                    .build());
        } else {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Register fail")
                    .message("Account existed")
                    .build());
        }
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
    public List<User> findAllUser() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            // Xử lý exception
            return Collections.emptyList();
        }
    }

    @Override
    public ResponseEntity<UpdateUserResponse> updateUser(String email, UserDto updateUserRequest) {
        var user = repository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Fail")
                    .message("User not found")
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
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Fail")
                    .message("ServletRequestAttributes not found")
                    .build());
        }

        user.setActive(updateUserRequest.isActive());
        return ResponseEntity.ok(UpdateUserResponse.builder()
                .status("Success")
                .message("Update User Success")
                .updateUser(user)
                .build());
    }

    @Override
    public ResponseEntity<ObjectResponse> deleteUser(String email) {
        var user = repository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setActive(false);
            repository.save(user);
            return ResponseEntity.ok().body(new ObjectResponse("Success","User deleted successfully"));
        } else {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Fail")
                    .message("User not found")
                    .build());
        }
    }

    @Override
    public User getUserById(Long id) {
        var user = repository.findUsersById(id);
        if(user == null){
            throw  new RuntimeException("Not found user");
        }
        return user;
    }

    private void saveToken (User user, String jwtToken){
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
}








