package com.ecomm.ecommservice.repository;

import com.ecomm.ecommservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findUserInfoByEmail(String email);
}
