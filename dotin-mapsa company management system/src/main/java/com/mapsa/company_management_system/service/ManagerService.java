package com.mapsa.company_management_system.service;

import com.mapsa.company_management_system.model.CompanyUser;
import com.mapsa.company_management_system.model.Manager;
import com.mapsa.company_management_system.model.dto.ManagerDto;
import com.mapsa.company_management_system.repository.CompanyUserRepository;
import com.mapsa.company_management_system.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();


    ManagerRepository managerRepository;
    CompanyUserRepository companyUserRepository;

    public Manager save(ManagerDto managerDto){
//        managerDto.setPassword(passwordEncoder.encode(managerDto.getPassword()));
        companyUserRepository.save(mapObjectToCompanyUser(managerDto));
        return managerRepository.save(mapObject(managerDto));
    }

    public Manager mapObject(ManagerDto managerDto){
        return modelMapper.map(managerDto,Manager.class);
    }

    public CompanyUser mapObjectToCompanyUser(ManagerDto managerDto){
        return modelMapper.map(managerDto,CompanyUser.class);
    }

    public Manager findByUsername(String username){
        return managerRepository.findByUsername(username);
    }

    public List<Manager> getAll(){
        return managerRepository.findAll();
    }
}