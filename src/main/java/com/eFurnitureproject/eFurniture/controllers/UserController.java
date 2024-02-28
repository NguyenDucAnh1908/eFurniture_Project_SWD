package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.ObjectResponse;
import com.eFurnitureproject.eFurniture.Responses.UpdateUserResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.services.impl.EmailService;
import com.eFurnitureproject.eFurniture.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth/")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/createUser")
    private ResponseEntity<ObjectResponse> createUser(@RequestBody UserDto request){
        try{
            return userService.createUser(request);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ObjectResponse
                    .builder()
                    .status("Fail")
                    .message("Register fail")
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
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
            }
            else {
                String pass = RandomStringUtils.randomAlphanumeric(6);

                user.setPassword(passwordEncoder.encode(pass));
                user = userService.saveUserForMail(user);
                emailService.sendSimpleMessage(email,"Reset password","New password is : " + pass);
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

    @GetMapping("/getAllUser")
    private List<User> getAll() {
        return userService.findAllUser();
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<UpdateUserResponse> updateStaff(
            @PathVariable String email,
            @RequestBody UserDto updateUserRequest) {
        try {
            return userService.updateUser(email,updateUserRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Update fail")
                    .message(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/getUserById/{id}")
    private User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("deleteUser/{email}")
    public ResponseEntity<ObjectResponse> deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }



}
