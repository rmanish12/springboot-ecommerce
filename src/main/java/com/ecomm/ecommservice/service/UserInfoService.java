package com.ecomm.ecommservice.service;

import com.ecomm.ecommservice.dto.request.RegisterUserRequest;
import com.ecomm.ecommservice.dto.response.UserProfileDto;

import java.util.UUID;

public interface UserInfoService {
    void registerUser(RegisterUserRequest registerUserRequest);
    UserProfileDto getUserDetails(UUID userId);
}
