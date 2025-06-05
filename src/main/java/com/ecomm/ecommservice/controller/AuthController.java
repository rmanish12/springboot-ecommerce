package com.ecomm.ecommservice.controller;

import com.ecomm.ecommservice.dto.request.RegisterUserRequest;
import com.ecomm.ecommservice.dto.response.ApiResponse;
import com.ecomm.ecommservice.dto.response.UserProfileDto;
import com.ecomm.ecommservice.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Auth Controller", description = "Controller for operations related to user authentication")
public class AuthController {

    private UserInfoService userInfoService;

    @Operation(summary = "Register New User")
    @PostMapping("/register")
    public ApiResponse<Void> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        userInfoService.registerUser(registerUserRequest);
        return ApiResponse.successWithMessage("User has been created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Getting user details by ID")
    @GetMapping("/{id}")
    public ApiResponse<UserProfileDto> getUserDetails(@PathVariable("id") UUID id) {
        return ApiResponse.success(userInfoService.getUserDetails(id));
    }
}
