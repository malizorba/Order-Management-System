package com.example.userservice.Controller;

import com.example.userservice.DTO.Request.AuthRequest;
import com.example.userservice.DTO.Response.AuthResponse;
import com.example.userservice.DTO.UserDto;
import com.example.userservice.Service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserDto userDto) {
        AuthResponse response = userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName(); // JwtToken içinden gelen kullanıcı email'i
        UserDto user = userService.getUserDetailsByEmail(email);
        return ResponseEntity.ok(user);
    }
}

