package com.ecomm.ecommservice.service;

import com.ecomm.ecommservice.dto.request.RegisterUserRequest;
import com.ecomm.ecommservice.entity.UserInfo;

import java.util.UUID;

public interface UserInfoService {
    void registerUser(RegisterUserRequest registerUserRequest);
    UserInfo getUserDetails(UUID userId);
}
