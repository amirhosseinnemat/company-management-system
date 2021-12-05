package com.mapsa.company_management_system.repository;

import com.mapsa.company_management_system.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager , Long> {

    Manager findByUsername(String username);

    Optional<Manager> findById(Long id);
}
