package com.mapsa.company_management_system.repository;

import com.mapsa.company_management_system.model.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser,Long> {
    CompanyUser findUserByUsername(String username);
}
