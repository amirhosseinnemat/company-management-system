package com.mapsa.company_management_system.security;

import com.mapsa.company_management_system.model.CompanyUser;
import com.mapsa.company_management_system.model.enums.EmployeeRole;
import com.mapsa.company_management_system.repository.CompanyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    CompanyUserRepository companyUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CompanyUser companyUser = companyUserRepository.findUserByUsername(username);
        if(companyUser == null){
            throw new UsernameNotFoundException("No such user: " + username);
        }
        List<EmployeeRole> roleList = new ArrayList<>();
        roleList.add(companyUser.getEmployeeRole());
        return new User(companyUser.getUsername(), companyUser.getPassword(), roleList);
    }
}
