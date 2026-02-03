package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmployeeRepo repo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(EmployeeRepo repo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String workEmail, String rawPassword) {

        Employee emp = repo.findByWorkEmailAndDeletedFalse(workEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getPassword() == null ||
                !passwordEncoder.matches(rawPassword, emp.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        //  UUID + Role (matches JwtUtil exactly)
        return jwtUtil.generateToken(
                emp.getEmployeeId(),
                emp.getRole()
        );
    }
}

