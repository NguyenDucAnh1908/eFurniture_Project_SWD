package com.eFurnitureproject.eFurniture.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String generateToken(HashMap<String,Object> extraClaims, UserDetails userDetails);
    String generateRefeshToken(UserDetails userDetails);
    boolean isTokenExpired(String token);
}
