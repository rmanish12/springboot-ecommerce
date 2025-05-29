package com.ecomm.ecommservice.repository;

import com.ecomm.ecommservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByCodeIn(Collection<String> codes);

    boolean existsByName(String name);
}
