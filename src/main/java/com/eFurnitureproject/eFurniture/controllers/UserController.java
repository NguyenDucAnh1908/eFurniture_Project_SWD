package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.AuthenticationResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.AuthenticationDTO;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    private ResponseEntity<UserResponse> createUser(@RequestBody UserDto request){
        try{
            return userService.createUser(request);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(UserResponse
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


}
