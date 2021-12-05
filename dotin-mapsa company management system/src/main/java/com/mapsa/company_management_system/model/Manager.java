package com.mapsa.company_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "managers")
@Component
@Getter
@Setter
public class Manager extends CompanyUser {

    private String firstName;
    private String lastName;

    @NotBlank(message = "You have to enter an email.")
    private String emailAddress;

    @OneToMany
    private List<Employee> employees;

}
