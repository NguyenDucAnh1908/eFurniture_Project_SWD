package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.models.Enum.TokenType;
import com.eFurnitureproject.eFurniture.models.Token;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.TokenRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern = Pattern.compile(emailRegex);

    @Override
    public ResponseEntity<UserResponse> createUser(UserDto request) {
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
            return ResponseEntity.ok().body(UserResponse.builder()
                    .message("Register success")
                    .user(user)
                    .build());
        } else {
            return ResponseEntity.badRequest().body(UserResponse.builder()
                    .user(null)
                    .message("Account existed")
                    .build());
        }
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
}


