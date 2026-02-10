package com.employee.management.service;

import com.employee.management.dto.AuthResponse;
import com.employee.management.entity.Employee;
import com.employee.management.entity.PasswordResetToken;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.PasswordResetRepo;
import com.employee.management.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final EmployeeRepo employeeRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepo resetRepo;
    private final SendGridEmailService mailService;

    public AuthService(EmployeeRepo repo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, PasswordResetRepo resetRepo, SendGridEmailService mailService) {
        this.employeeRepo = repo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.resetRepo = resetRepo;
        this.mailService = mailService;
    }

    public AuthResponse login(String workEmail, String rawPassword) {

        Employee emp = employeeRepo.findByWorkEmailAndDeletedFalse(workEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getPassword() == null ||
                !passwordEncoder.matches(rawPassword, emp.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        //  UUID + Role (matches JwtUtil exactly)
        String token = jwtUtil.generateToken(
                emp.getEmployeeId(),
                emp.getRole()
        );
        return new AuthResponse(token,emp.getRole());
    }

    public void forgotPassword(String email) {
        Employee employee= employeeRepo.findByWorkEmailAndDeletedFalse(email)
                .orElse(null);

        if (employee==null) return;

        String token= UUID.randomUUID().toString();

        PasswordResetToken reset=new PasswordResetToken();
        reset.setToken(token);
        reset.setEmployee(employee);
        reset.setExpiryTime(LocalDateTime.now().plusMinutes(15));

        resetRepo.save(reset);
        mailService.sendEmail(
                email,
                "Password Reset ",
                "Click to reset: http://localhost:3000/reset?token="+token
        );
    }

    public void resetPassword(String token, String newPassword) {

        PasswordResetToken reset =resetRepo.findByToken(token)
                .orElseThrow(()->new RuntimeException("token is not valid "));

        if (reset.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("token is expired..");
        }

        Employee emp= reset.getEmployee();
        emp.setPassword(passwordEncoder.encode(newPassword));
        employeeRepo.save(emp);

        resetRepo.delete(reset);

    }
}

