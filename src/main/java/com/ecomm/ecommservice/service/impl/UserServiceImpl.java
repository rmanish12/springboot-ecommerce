package com.ecomm.ecommservice.service.impl;

import com.ecomm.ecommservice.dto.request.UpdateUserRoleRequestDto;
import com.ecomm.ecommservice.entity.Role;
import com.ecomm.ecommservice.entity.UserInfo;
import com.ecomm.ecommservice.exception.NotFoundException;
import com.ecomm.ecommservice.repository.RoleRepository;
import com.ecomm.ecommservice.repository.UserInfoRepository;
import com.ecomm.ecommservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserInfoRepository userInfoRepository;
    private RoleRepository roleRepository;

    @Override
    public void addRoleToUser(UUID userId, UpdateUserRoleRequestDto updateUserRoleRequestDto) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));

        List<Role> roles = roleRepository.findByCodeIn(updateUserRoleRequestDto.getRoles());
        userInfo.setRoles(roles);

        userInfoRepository.save(userInfo);
    }

    @Override
    public List<Role> getUserRoles(UUID userId) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));
        return userInfo.getRoles();
    }

    @Override
    public Set<String> getUserPermissions(UUID userId) {
        Set<String> userPermissions = new HashSet<>();

        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "userId", userId));

        userInfo
                .getRoles()
                .forEach(role -> role.getPermissions()
                        .forEach(permission -> {
                            userPermissions.add(permission.getCode());
                        })
                );

        return userPermissions;
    }
}
