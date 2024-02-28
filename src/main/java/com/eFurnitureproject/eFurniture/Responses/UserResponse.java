package com.eFurnitureproject.eFurniture.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String password;
    private boolean active;
    private Date dateOfBirth;
    private int facebookAccountId;
    private int googleAccountId;

}

