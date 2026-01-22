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

    public String login(String workEmail, String password) {

        Employee emp = repo.findByWorkEmailAndDeletedFalse(workEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getPassword() == null || !emp.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ UUID + Role (matches JwtUtil exactly)
        return jwtUtil.generateToken(
                emp.getEmployeeId(),
                emp.getRole()
        );
    }
}

