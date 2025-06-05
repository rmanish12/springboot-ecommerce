package com.ecomm.ecommservice.service.impl;

import com.ecomm.ecommservice.dto.request.LoginUserDto;
import com.ecomm.ecommservice.dto.request.RegisterUserRequest;
import com.ecomm.ecommservice.dto.response.TokenDto;
import com.ecomm.ecommservice.dto.response.UserProfileDto;
import com.ecomm.ecommservice.entity.UserInfo;
import com.ecomm.ecommservice.entity.UserProfile;
import com.ecomm.ecommservice.exception.BadCredentials;
import com.ecomm.ecommservice.exception.ConflictException;
import com.ecomm.ecommservice.exception.NotFoundException;
import com.ecomm.ecommservice.mapper.UserProfileDtoMapper;
import com.ecomm.ecommservice.repository.UserProfileRepository;
import com.ecomm.ecommservice.repository.UserInfoRepository;
import com.ecomm.ecommservice.security.JwtService;
import com.ecomm.ecommservice.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;
    private UserProfileRepository userProfileRepository;
    private UserProfileDtoMapper userProfileDtoMapper;
    private JwtService jwtService;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        log.info("Request received for registering user with email: {}", registerUserRequest.getEmail());
        UserInfo savedUserInfo = userInfoRepository.findUserInfoByEmail(registerUserRequest.getEmail())
                .orElse(null);

        if (savedUserInfo != null) {
            log.error("User with given email exists: {}", registerUserRequest.getEmail());
            throw new ConflictException("User", "email", registerUserRequest.getEmail());
        }

        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setFirstName(registerUserRequest.getFirstName());
        newUserProfile.setLastName(registerUserRequest.getLastName());

        UserProfile createdUserProfile = userProfileRepository.save(newUserProfile);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserProfile(createdUserProfile);
        userInfo.setEmail(registerUserRequest.getEmail());
        userInfo.setPassword(registerUserRequest.getPassword());

        userInfoRepository.save(userInfo);

        log.info("User with given email registered successfully: {}", registerUserRequest.getEmail());
    }

    @Override
    public UserProfileDto getUserDetails(UUID userId) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));
        return userProfileDtoMapper.toDto(userInfo);
    }

    @Override
    public TokenDto loginUser(LoginUserDto loginUserDto) {
        log.info("Request received for logging user with email - {}", loginUserDto.getEmail());
        UserInfo userInfo = userInfoRepository.findUserInfoByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new BadCredentials("User", "email", loginUserDto.getEmail()));

        if (!loginUserDto.getPassword().equals(userInfo.getPassword())) {
            throw new BadCredentials("User", "email", loginUserDto.getEmail());
        }

        log.info("Generating token for user - {}", loginUserDto.getEmail());
        String token = jwtService.generateToken(loginUserDto.getEmail());

        return new TokenDto(token);

    }
}
