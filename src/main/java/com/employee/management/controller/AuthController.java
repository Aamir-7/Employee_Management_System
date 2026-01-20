package com.employee.management.controller;

import com.employee.management.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String,String>login(@RequestBody Map<String,String>req){
       String token=authService.login(
               req.get("username"),
               req.get("password")
       );
       return Map.of("token",token);

    }
}
