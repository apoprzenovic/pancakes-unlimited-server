package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
