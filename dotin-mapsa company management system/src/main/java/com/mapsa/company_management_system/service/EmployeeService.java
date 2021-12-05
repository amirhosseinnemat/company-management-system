package com.mapsa.company_management_system.service;

import com.mapsa.company_management_system.model.CompanyUser;
import com.mapsa.company_management_system.model.Employee;
import com.mapsa.company_management_system.model.dto.EmployeeDto;
import com.mapsa.company_management_system.model.dto.ManagerDto;
import com.mapsa.company_management_system.repository.CompanyUserRepository;
import com.mapsa.company_management_system.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();


    private EmployeeRepository employeeRepository;
    CompanyUserRepository companyUserRepository;


    public Employee save(EmployeeDto employeeDto){
//        employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        companyUserRepository.save(mapObjectToCompanyUser(employeeDto));
        return employeeRepository.save(mapObject(employeeDto));
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee mapObject(EmployeeDto employeeDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeDto,Employee.class);
    }

    public CompanyUser mapObjectToCompanyUser(EmployeeDto employeeDto){
        return modelMapper.map(employeeDto,CompanyUser.class);
    }

    public Employee findByUsername(String username){
        return employeeRepository.findByUsername(username);
    }

}
