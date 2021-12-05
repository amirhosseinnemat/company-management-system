package com.mapsa.company_management_system.repository;

import com.mapsa.company_management_system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Long> {

    Employee findByUsername(String username);
}
