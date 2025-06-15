package com.example.userservice.Service.Implementation;

import com.example.common.Exception.BusinessException;
import com.example.common.Exception.ErrorCode;
import com.example.userservice.DTO.Request.AuthRequest;
import com.example.userservice.DTO.Response.AuthResponse;
import com.example.userservice.DTO.UserDto;
import com.example.userservice.DTO.UserRole;
import com.example.userservice.Entity.UserEntity;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.IUserService;
import com.example.userservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        UserEntity user = UserEntity.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .role(UserRole.USER) // 🛡️ Sabit olarak USER atanıyor
                .build();

        userRepository.save(user);

        return generateToken(user); // Token içinde rol bilgisi olabilir
    }
    public AuthResponse login(AuthRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        return generateToken(user);
    }
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
    private AuthResponse generateToken(UserEntity user) {
        Map<String, Object> claims = Map.of("role", user.getRole());
        String token = jwtTokenProvider.generateToken(user.getEmail(),claims);
        return new AuthResponse(token);
    }
}
