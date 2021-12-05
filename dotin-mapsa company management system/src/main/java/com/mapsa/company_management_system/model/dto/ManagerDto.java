package com.mapsa.company_management_system.model.dto;

import com.mapsa.company_management_system.model.enums.EmployeeRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ManagerDto {

    @NotBlank(message = "You have to enter a username.")
    private String username;

    @NotBlank(message = "You have to enter a password.")
    private String password;
    private String firstName;
    private String lastName;

    @NotBlank(message = "You have to enter an email.")
    private String emailAddress;

    protected EmployeeRole employeeRole;
}
