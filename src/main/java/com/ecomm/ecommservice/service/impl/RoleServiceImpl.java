package com.ecomm.ecommservice.service.impl;

import com.ecomm.ecommservice.dto.request.CreatUpdateRoleRequestDto;
import com.ecomm.ecommservice.entity.Permission;
import com.ecomm.ecommservice.entity.Role;
import com.ecomm.ecommservice.exception.ConflictException;
import com.ecomm.ecommservice.exception.NotFoundException;
import com.ecomm.ecommservice.repository.PermissionRepository;
import com.ecomm.ecommservice.repository.RoleRepository;
import com.ecomm.ecommservice.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Override
    public void createRole(CreatUpdateRoleRequestDto createRoleDto) {
        boolean roleExist = roleRepository.existsByName(createRoleDto.getName());

        if (roleExist) {
            throw new ConflictException("Role", "name", createRoleDto.getName());
        };

        String roleCode = createRoleDto.getName().replace(" ", "_").toUpperCase();

        List<Permission> rolePermissions = permissionRepository.findPermissionsByCodeIn(createRoleDto.getPermissions());

        Role newRole = new Role();
        newRole.setName(createRoleDto.getName());
        newRole.setCode(roleCode.toUpperCase());
        newRole.setPermissions(rolePermissions);

        roleRepository.save(newRole);
    }

    @Override
    public Role getRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role", "id", id));
    }

    @Override
    public void updateRole(UUID id, CreatUpdateRoleRequestDto updateRoleRequestDto) {
        Role roleToUpdate = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role", "id", id));

        List<Permission> newPermissions = permissionRepository.findPermissionsByCodeIn(updateRoleRequestDto.getPermissions());

        roleToUpdate.setName(updateRoleRequestDto.getName());
        roleToUpdate.setPermissions(newPermissions);

        roleRepository.save(roleToUpdate);
    }

    @Override
    @Transactional
    public void deleteRole(UUID id) {
        Role roleToDelete = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role", "id", id));
        roleRepository.delete(roleToDelete);
    }
}
