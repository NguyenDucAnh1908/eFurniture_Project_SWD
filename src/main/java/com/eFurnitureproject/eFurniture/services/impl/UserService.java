package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UpdateUserReponse.UpdateUserResponse;
import com.eFurnitureproject.eFurniture.Responses.UserDetailResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.AdditionalInfoDto;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.UserStatsDTO;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.*;
import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.models.Enum.TokenType;
import com.eFurnitureproject.eFurniture.repositories.*;
import com.eFurnitureproject.eFurniture.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final BookingRepository bookingRepository;
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
                .address(request.getAddress())
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

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .active(user.isActive())
                .role(user.getRole())
                .address(user.getAddress())
                .build();
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        try {
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
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .staus("Fail")
                    .messages("Login fail")
                    .user(null)
                    .build();
        }
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
        return null;
    }


    @Override
    public UserResponse getUserById(Long userId) {
        var user = repository.findById(userId).orElse(null);
        if (user != null) {
            return convertToUserResponse(user);
        }
        return null;
    }

    @Override
    public ResponseEntity<ObjectResponse> deleteUser(String email) {
        return null;
    }

    @Override
    public ResponseEntity<UpdateUserResponse> updateUser(String email, UserDto updateUserRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ObjectResponse> deleteUser(Long userId) {
        var user = repository.findById(userId).orElse(null);
        if (user != null) {
            user.setActive(false);
            repository.save(user);
            return ResponseEntity.ok().body(new ObjectResponse("Success", "User deleted successfully", convertToUserResponse(user)));
        } else {
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status("Fail")
                    .message("User not found")
                    .build());
        }
    }

    @Override
    public ResponseEntity<UpdateUserResponse> updateUser(Long userId, UserDto updateUserRequest) {
        var user = repository.findById(userId).orElse(null);
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
        if (updateUserRequest.getAddress() != null && !updateUserRequest.getAddress().isEmpty()) {
            user.setPassword(updateUserRequest.getAddress());
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Fail")
                    .message("ServletRequestAttributes not found")
                    .build());
        }
        if (updateUserRequest.getRole() != null) {
            user.setRole(updateUserRequest.getRole());
        }
        user.setActive(updateUserRequest.isActive());
        repository.save(user);
        return ResponseEntity.ok(UpdateUserResponse.builder()
                .status("Success")
                .message("Update User Success")
                .update(user)
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

    private void revokeAllUsserTokens(User user) {
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

    public void receiveAndConfirmConsultation(Long id, AdditionalInfoDto additionalInfoDto) throws DataNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Booking request not found with ID: " + id));
        User designer = repository.findById(additionalInfoDto.getDesignerId())
                .orElseThrow(() -> new DataNotFoundException("Designer not found with ID: " + additionalInfoDto.getDesignerId()));
        booking.setStatus("Confirmed");
        booking.setDesigner(designer);
        booking.setSchedule(additionalInfoDto.getSchedule());
        bookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long bookingId) throws DataNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            // Check if the booking status is not "Confirmed" before canceling
            if (!"Confirmed".equals(booking.getStatus())) {
                throw new DataNotFoundException("Booking cannot be canceled because it is not in the Confirmed status.");
            }

            // Update the booking status to "Cancel"
            booking.setStatus("Cancel");
            bookingRepository.save(booking);
        } else {
            throw new DataNotFoundException("Booking not found with ID: " + bookingId);
        }
        return ;
//        return userPage.map(this::convertToUserResponse);
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public Page<UserResponse> getAllUsers(PageRequest pageRequest, Role role) {
        return null;
    }


    @Override
    public ResponseEntity<UserDetailResponse> findUserDetail(Long userId) {
        User userDetail = repository.findById(userId).orElse(null);
        if (userDetail != null) {
            Optional<Address> address = addressRepository.findByUserId(userId);
            List<Order> orders = orderRepository.findByUserId(userId);
            UserResponse userResponse = UserResponse.builder()
                    .id(userDetail.getId())
                    .fullName(userDetail.getFullName())
                    .phoneNumber(userDetail.getPhoneNumber())
                    .dateOfBirth(userDetail.getDateOfBirth())
                    .build();

            return ResponseEntity.ok().body(UserDetailResponse.builder()
                    .userdetail(userResponse)
                    .address(address.orElse(null))
                    .order(orders)
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }


    }
}








