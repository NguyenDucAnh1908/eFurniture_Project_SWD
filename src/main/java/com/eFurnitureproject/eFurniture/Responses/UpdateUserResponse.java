package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserResponse {
    private String status;
    private String message;
    private User updateUser;
}
