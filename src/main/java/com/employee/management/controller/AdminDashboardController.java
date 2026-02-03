package com.employee.management.controller;

import com.employee.management.dto.AdminDashboardResponse;
import com.employee.management.service.AdminDashboardService;
import com.employee.management.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/admin")
public class AdminDashboardController {

    private final JwtUtil jwtUtil;
    private final AdminDashboardService service;

    public AdminDashboardController(JwtUtil jwtUtil, AdminDashboardService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @GetMapping("/summary")
    public AdminDashboardResponse getSummary(
            @RequestHeader("Authorization")String authHeader
    ){
        jwtUtil.enforceAdminOrManager(authHeader);
        return service.getSummary();
    }
}
