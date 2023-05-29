package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link Roles} Entity
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
}
