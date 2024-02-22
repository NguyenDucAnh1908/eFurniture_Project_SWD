package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.UserResponse;
import com.eFurnitureproject.eFurniture.dtos.UserDto;
import com.eFurnitureproject.eFurniture.services.impl.UserService;
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
                    .message("Register fail")
                    .build());
        }
    }


}
