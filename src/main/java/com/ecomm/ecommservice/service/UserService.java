package com.ecomm.ecommservice.service;

import com.ecomm.ecommservice.dto.request.UpdateUserRoleRequestDto;
import com.ecomm.ecommservice.entity.Role;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    void addRoleToUser(UUID userId, UpdateUserRoleRequestDto updateUserRoleRequestDto);
    List<Role> getUserRoles(UUID userId);
    Set<String> getUserPermissions(UUID userId);
}
