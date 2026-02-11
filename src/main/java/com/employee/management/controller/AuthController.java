package com.employee.management.controller;

import com.employee.management.dto.AuthResponse;
import com.employee.management.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}

