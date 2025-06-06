package com.ecomm.ecommservice.service.impl;

import com.ecomm.ecommservice.dto.request.LoginUserDto;
import com.ecomm.ecommservice.dto.request.RegisterUserRequest;
import com.ecomm.ecommservice.dto.response.TokenDto;
import com.ecomm.ecommservice.dto.response.UserProfileDto;
import com.ecomm.ecommservice.entity.User;
import com.ecomm.ecommservice.entity.UserProfile;
import com.ecomm.ecommservice.exception.BadCredentials;
import com.ecomm.ecommservice.exception.ConflictException;
import com.ecomm.ecommservice.exception.NotFoundException;
import com.ecomm.ecommservice.mapper.UserProfileDtoMapper;
import com.ecomm.ecommservice.repository.UserProfileRepository;
import com.ecomm.ecommservice.repository.UserRepository;
import com.ecomm.ecommservice.security.JwtService;
import com.ecomm.ecommservice.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    private UserProfileDtoMapper userProfileDtoMapper;
    private JwtService jwtService;
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        log.info("Request received for registering user with email: {}", registerUserRequest.getEmail());
        User savedUserInfo = userRepository.findByEmail(registerUserRequest.getEmail())
                .orElse(null);

        if (savedUserInfo != null) {
            log.error("User with given email exists: {}", registerUserRequest.getEmail());
            throw new ConflictException("User", "email", registerUserRequest.getEmail());
        }

        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setFirstName(registerUserRequest.getFirstName());
        newUserProfile.setLastName(registerUserRequest.getLastName());

        UserProfile createdUserProfile = userProfileRepository.save(newUserProfile);

        User userInfo = new User();
        userInfo.setUserProfile(createdUserProfile);
        userInfo.setEmail(registerUserRequest.getEmail());
        userInfo.setPassword(registerUserRequest.getPassword());

        userRepository.save(userInfo);

        log.info("User with given email registered successfully: {}", registerUserRequest.getEmail());
    }

    @Override
    public UserProfileDto getUserDetails(UUID userId) {
        User userInfo = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));
        return userProfileDtoMapper.toDto(userInfo);
    }

    @Override
    public TokenDto loginUser(LoginUserDto loginUserDto) {
        log.info("Request received for logging user with email - {}", loginUserDto.getEmail());
        User userInfo = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new BadCredentials("User", "email", loginUserDto.getEmail()));

        if (!loginUserDto.getPassword().equals(userInfo.getPassword())) {
            throw new BadCredentials("User", "email", loginUserDto.getEmail());
        }

        log.info("Generating token for user - {}", loginUserDto.getEmail());
        String token = jwtService.generateToken(loginUserDto.getEmail());

        redisTemplate.opsForValue().set(token, loginUserDto.getEmail());
        redisTemplate.expire(token, 60, TimeUnit.MINUTES);
        return new TokenDto(token);

    }
}
