package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeRepo repo;
    private final JwtUtil jwtUtil;

    public AuthService(EmployeeRepo repo, JwtUtil jwtUtil) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        Employee emp=repo.findByUsernameAndDeletedFalse(username)
                .orElseThrow(()->new RuntimeException("Employee not found"));

        if (!emp.getPassword().equals(password)){
            throw new RuntimeException("invalid credentials");
        }

        return jwtUtil.generateToken(emp.getUsername(),emp.getRole().name());
    }
}
