package com.mapsa.company_management_system.model.dto;

import com.mapsa.company_management_system.model.Manager;
import com.mapsa.company_management_system.model.enums.EmployeeRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
public class EmployeeDto implements Serializable {

    @NotBlank(message = "You have to enter a username.")
    private String username;

    @NotBlank(message = "You have to enter a password.")
    private String password;

    private String firstName;
    private String lastName;

    @NotBlank(message = "You have to enter an email.")
    private String emailAddress;

    @NotNull(message = "You have to choose a role.")
    private EmployeeRole employeeRole;

    @NotNull(message = "You have to choose a manager.")
    private Manager manager;

}
