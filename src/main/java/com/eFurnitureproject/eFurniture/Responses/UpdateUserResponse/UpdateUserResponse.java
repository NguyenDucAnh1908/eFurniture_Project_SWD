package com.eFurnitureproject.eFurniture.Responses.UpdateUserResponse;

import com.eFurnitureproject.eFurniture.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserResponse {
    private String status;
    private String message;
    private User update;
}
