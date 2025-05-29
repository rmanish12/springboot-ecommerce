package com.ecomm.ecommservice.service;

import com.ecomm.ecommservice.dto.request.CreatUpdateRoleRequestDto;
import com.ecomm.ecommservice.entity.Role;

import java.util.UUID;

public interface RoleService {
    void createRole(CreatUpdateRoleRequestDto createRoleDto);
    Role getRoleById(UUID id);
    void updateRole(UUID id, CreatUpdateRoleRequestDto updateRoleRequestDto);
    void deleteRole(UUID id);
}
