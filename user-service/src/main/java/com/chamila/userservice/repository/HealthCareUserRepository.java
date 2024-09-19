package com.chamila.userservice.repository;

import com.chamila.userservice.model.HealthCareUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCareUserRepository extends JpaRepository<HealthCareUser, Integer> {

    HealthCareUser findByUsername(String username);
}
