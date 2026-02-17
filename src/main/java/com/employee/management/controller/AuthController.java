package com.employee.management.controller;

import com.employee.management.dto.AuthResponse;
import com.employee.management.entity.Employee;
import com.employee.management.entity.RefreshToken;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.service.AuthService;
import com.employee.management.service.RefreshTokenService;
import com.employee.management.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final EmployeeRepo employeeRepo;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, EmployeeRepo employeeRepo, JwtUtil jwtUtil) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.employeeRepo = employeeRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Map<String, String> req) {

        String workEmail = req.get("workEmail");
        String password = req.get("password");

        if (workEmail == null || password == null) {
            throw new RuntimeException("workEmail and password are required");
        }

        return authService.login(workEmail, password);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody Map<String,String>body
    ){
        authService.forgotPassword(body.get("workEmail"));
        return "reset link sent if email exists ";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody Map<String,String>body
    ){
        authService.resetPassword(
                body.get("token"),
                body.get("newPassword")
        );
        return "Password updated successfully";
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(
            @RequestBody Map<String,String>body
    ){
        String refreshToken=body.get("refreshToken");

        RefreshToken refresh=refreshTokenService.validateToken(refreshToken);

        Employee emp = employeeRepo
                .findByEmployeeIdAndDeletedFalse(refresh.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken=jwtUtil.generateToken(emp.getEmployeeId(),emp.getRole());

        return new AuthResponse(
                newAccessToken,
                emp.getRole(),
                refreshToken
        );
    }
}

