package com.ecomm.ecommservice.controller;

import com.ecomm.ecommservice.dto.request.UpdateUserRoleRequestDto;
import com.ecomm.ecommservice.dto.response.ApiResponse;
import com.ecomm.ecommservice.dto.response.UserProfileDto;
import com.ecomm.ecommservice.entity.Role;
import com.ecomm.ecommservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Controller for performing operations on a User")
public class UserController {

    private UserService userService;

    @Operation(summary = "Get User Profile")
    @GetMapping("/profile")
    public ApiResponse<UserProfileDto> getUserProfile() {
        return ApiResponse.success(userService.getUserProfile());
    }

    @Operation(summary = "Update User Roles")
    @PutMapping("/{id}/roles")
    public ApiResponse<Void> updateUserRoles(@PathVariable("id") UUID userId, @Valid @RequestBody UpdateUserRoleRequestDto updateUserRoleRequestDto) {
        userService.addRoleToUser(userId, updateUserRoleRequestDto);
        return ApiResponse.successWithMessage("Roles has been associated with the user");
    }

    @Operation(summary = "Get User Roles")
    @GetMapping("/{id}/roles")
    public ApiResponse<List<Role>> getUserRoles(@PathVariable("id") UUID userId) {
        return ApiResponse.success(userService.getUserRoles(userId));
    }

    @Operation(summary = "Get User Permission")
    @GetMapping("/{id}/permissions")
    public ApiResponse<Set<String>> getUserPermissions(@PathVariable("id") UUID userId) {
        return ApiResponse.success(userService.getUserPermissions(userId));
    }
}
