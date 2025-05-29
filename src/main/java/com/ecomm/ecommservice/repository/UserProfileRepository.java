package com.ecomm.ecommservice.repository;

import com.ecomm.ecommservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
}
