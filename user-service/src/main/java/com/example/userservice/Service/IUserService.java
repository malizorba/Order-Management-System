package com.example.userservice.Service;

import com.example.userservice.DTO.Request.AuthRequest;
import com.example.userservice.DTO.Response.AuthResponse;
import com.example.userservice.DTO.UserDto;
import com.example.userservice.Entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    AuthResponse register(UserDto user);
    AuthResponse login(AuthRequest request);
    UserDto getUserDetailsByEmail(String email);
}