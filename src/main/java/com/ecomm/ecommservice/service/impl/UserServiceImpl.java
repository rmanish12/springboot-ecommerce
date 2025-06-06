package com.ecomm.ecommservice.service.impl;

import com.ecomm.ecommservice.dto.request.UpdateUserRoleRequestDto;
import com.ecomm.ecommservice.dto.response.UserProfileDto;
import com.ecomm.ecommservice.entity.Role;
import com.ecomm.ecommservice.entity.User;
import com.ecomm.ecommservice.exception.NotFoundException;
import com.ecomm.ecommservice.mapper.UserProfileDtoMapper;
import com.ecomm.ecommservice.repository.RoleRepository;
import com.ecomm.ecommservice.repository.UserRepository;
import com.ecomm.ecommservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserProfileDtoMapper userProfileDtoMapper;

    @Override
    public void addRoleToUser(UUID userId, UpdateUserRoleRequestDto updateUserRoleRequestDto) {
        User userInfo = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));

        List<Role> roles = roleRepository.findByCodeIn(updateUserRoleRequestDto.getRoles());
        userInfo.setRoles(roles);

        userRepository.save(userInfo);
    }

    @Override
    public List<Role> getUserRoles(UUID userId) {
        User userInfo = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));
        return userInfo.getRoles();
    }

    @Override
    public Set<String> getUserPermissions(UUID userId) {
        Set<String> userPermissions = new HashSet<>();

        User userInfo = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));

        userInfo
                .getRoles()
                .forEach(role -> role.getPermissions()
                        .forEach(permission -> {
                            userPermissions.add(permission.getCode());
                        })
                );

        return userPermissions;
    }

    @Override
    public UserProfileDto getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User userInfo = userRepository.findByEmail(userEmail).get();

        return userProfileDtoMapper.toDto(userInfo);
    }
}
