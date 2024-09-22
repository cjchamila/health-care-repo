package com.chamila.userservice.repository;

import com.chamila.userservice.model.HealthCareUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCareUserRoleRepository extends JpaRepository<HealthCareUserRole,Long> {
    HealthCareUserRole findByName(String roleUser);
}
