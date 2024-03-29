
package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private boolean active;
    private Role role;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private int facebookAccountId;
    private int googleAccountId;
    private String email;
}

