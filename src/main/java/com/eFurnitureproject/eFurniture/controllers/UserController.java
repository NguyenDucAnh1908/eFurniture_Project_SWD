
package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.*;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.UserStatsDTO;
import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UpdateUserResponse.UpdateUserResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.UserStatsDTO;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('user:create')")
    private ResponseEntity<ObjectResponse> createUser(@RequestBody UserDto request) {
        try {
            return userService.createUser(request);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ObjectResponse
                    .builder()
                    .status("Fail")
                    .message("Register fail")
                    .userResponse(null)
                    .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDTO request) {
        try {
            return ResponseEntity.ok(userService.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/refreshtoken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        userService.refreshToken(request, response);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> register(@RequestParam String email) {
        try {
            User user = userService.findByEmailForMail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
            } else {
                String pass = RandomStringUtils.randomAlphanumeric(6);

                user.setPassword(passwordEncoder.encode(pass));
                user = userService.saveUserForMail(user);
                emailService.sendSimpleMessage(email, "Reset password", "New password is : " + pass);
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            new ObjectResponse();
            return ResponseEntity.badRequest().body(ObjectResponse.builder()
                    .status(e.getMessage())
                    .message("Register fail")
                    .build());
        }
    }


    @GetMapping("get-all-user")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserListResponse> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").descending()
        );
        Page<UserResponse> userPage = userService.getAllUsers(pageRequest, Role.USER);
        int totalPages = userPage.getTotalPages();
        Long totalUsers = userPage.getTotalElements();
        List<UserResponse> users = userPage.getContent();
        return ResponseEntity.ok(UserListResponse.builder()
                .userResponses(users)
                .totalPages(totalPages)
                .totalUser(totalUsers)
                .build());
    }

    @GetMapping("get-all-staff")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserListResponse> getAllStaff(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").descending()
        );
        Page<UserResponse> userPage = userService.getAllUsers(pageRequest, Role.STAFF);
        int totalPages = userPage.getTotalPages();
        Long totalUsers = userPage.getTotalElements();
        List<UserResponse> users = userPage.getContent();
        return ResponseEntity.ok(UserListResponse.builder()
                .userResponses(users)
                .totalPages(totalPages)
                .totalUser(totalUsers)
                .build());
    }

    @GetMapping("get-all-staff-delivery")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserListResponse> getAllStaffDelivery(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").descending()
        );
        Page<UserResponse> userPage = userService.getAllUsers(pageRequest, Role.STAFF_DELIVERY);
        int totalPages = userPage.getTotalPages();
        Long totalUsers = userPage.getTotalElements();
        List<UserResponse> users = userPage.getContent();
        return ResponseEntity.ok(UserListResponse.builder()
                .userResponses(users)
                .totalPages(totalPages)
                .totalUser(totalUsers)
                .build());
    }

    @GetMapping("get-all-designer")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserListResponse> getAllDesigner(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").descending()
        );
        Page<UserResponse> userPage = userService.getAllUsers(pageRequest, Role.DESIGNER);
        int totalPages = userPage.getTotalPages();
        Long totalUsers = userPage.getTotalElements();
        List<UserResponse> users = userPage.getContent();
        return ResponseEntity.ok(UserListResponse.builder()
                .userResponses(users)
                .totalPages(totalPages)
                .totalUser(totalUsers)
                .build());
    }


    @GetMapping("")
    private List<User> getAll() {
        return userService.findAllUser();
    }


    @PutMapping("/updateUser/{userId}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<UpdateUserResponse> updateStaff(
            @PathVariable Long userId,
            @RequestBody UserDto updateUserRequest) {
        try {
            return userService.updateUser(userId, updateUserRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Update fail")
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("{userId}")
    private UserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("deleteUser/{userId}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<ObjectResponse> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/user-stats")
    public ResponseEntity<UserStatsDTO> getUserStats() {
        UserStatsDTO userStatsDTO = userService.getUserStats();
        String formattedPercentageChange = userStatsDTO.getFormattedPercentageChange();
        userStatsDTO.setPercentageChange(formattedPercentageChange != null ? Double.valueOf(formattedPercentageChange) : null);
        return ResponseEntity.ok(userStatsDTO);
    }


//    @GetMapping("/user-detail/{id}")
//    private ResponseEntity<UserDetailResponse> userDetail(@PathVariable Long id){
//        return userService.findUserDetail(id);
//    }

}

