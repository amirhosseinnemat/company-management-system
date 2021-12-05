package com.mapsa.company_management_system.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum EmployeeRole implements GrantedAuthority {
    DEVELOPER,
    TESTER,
    MANAGER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
