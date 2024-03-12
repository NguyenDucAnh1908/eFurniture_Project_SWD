package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String staus;
    private String messages;
    private UserResponse user;
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("refesh_token")
    private String refeshToken;
}
