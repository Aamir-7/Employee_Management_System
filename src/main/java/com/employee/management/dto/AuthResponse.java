package com.employee.management.dto;

import com.employee.management.enums.Role;

public class AuthResponse {
    private String token;
    private Role role;

    public AuthResponse(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}
