package com.ecomm.ecommservice.controller;

import com.ecomm.ecommservice.dto.request.CreatUpdateRoleRequestDto;
import com.ecomm.ecommservice.dto.response.ApiResponse;
import com.ecomm.ecommservice.entity.Role;
import com.ecomm.ecommservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
@Tag(name = "Role Controller", description = "Controller for performing operations on Role")
public class RoleController {

    private RoleService roleService;

    @Operation(summary = "Create new role")
    @PostMapping
    public ApiResponse<Void> createRole(@RequestBody CreatUpdateRoleRequestDto createRoleRequestDto) {
        roleService.createRole(createRoleRequestDto);
        return ApiResponse.successWithMessage("Role has been created successfully");
    }

    @Operation(summary = "Get role by ID")
    @GetMapping("/{id}")
    public ApiResponse<Role> getRoleById(@PathVariable("id") UUID id) {
        Role roleDetails = roleService.getRoleById(id);
        return ApiResponse.success(roleDetails);
    }

    @Operation(summary = "Update role by ID")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateRole(@PathVariable("id") UUID id, @Valid @RequestBody CreatUpdateRoleRequestDto updateRoleRequestDto) {
        roleService.updateRole(id, updateRoleRequestDto);
        return ApiResponse.successWithMessage("Role has been updated successfully");
    }

    @Operation(summary = "Delete role by ID")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable("id") UUID id) {
        roleService.deleteRole(id);
        return ApiResponse.successWithMessage("Role has been deleted successfully");
    }
}
