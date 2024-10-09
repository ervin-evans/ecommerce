package com.ecommerce.users.repository;

import com.ecommerce.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Boolean existsRoleByName(String name);
}
