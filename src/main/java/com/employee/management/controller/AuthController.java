package com.employee.management.controller;

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
    public Map<String, String> login(@RequestBody Map<String, String> req) {

        String workEmail = req.get("workEmail");
        String password = req.get("password");

        if (workEmail == null || password == null) {
            throw new RuntimeException("workEmail and password are required");
        }

        String token = authService.login(workEmail, password);
        return Map.of("token", token);
    }
}

