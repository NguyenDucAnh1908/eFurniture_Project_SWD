package com.eFurnitureproject.eFurniture.dtos;

import com.eFurnitureproject.eFurniture.models.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String fullName;
    private String phoneNumber;
    private String password;
    private boolean active;
    private Date dateOfBirth;
    private String email;
    private Role role;
    private String address;

}
