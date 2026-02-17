package com.employee.management.dto;

import com.employee.management.enums.Role;

public class AuthResponse {
    private String token;
    private Role role;
    private String refreshToken;

    public AuthResponse(String token, Role role, String refreshToken) {
        this.token = token;
        this.role = role;
        this.refreshToken = refreshToken;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
