package com.mapsa.company_management_system.model;

import com.mapsa.company_management_system.model.enums.EmployeeRole;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class CompanyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @NotBlank(message = "You have to enter a username.")
    @Column(name = "username", unique = true, nullable = false)
    protected String username;

    @NotBlank(message = "You have to enter a password.")
    @Min(value = 6, message = "Your password must have at least 6 characters.")
    @Column(name = "password", nullable = false, length = 1000)
    protected String password;

    @Enumerated(value = EnumType.STRING)
    protected EmployeeRole employeeRole;



}
