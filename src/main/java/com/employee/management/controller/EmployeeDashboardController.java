package com.employee.management.controller;

import com.employee.management.dto.EmployeeDashboardResponse;
import com.employee.management.service.EmployeeDashboardService;
import com.employee.management.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("dashboard/employee")
public class EmployeeDashboardController {

    private final JwtUtil jwtUtil;
    private final EmployeeDashboardService service;

    public EmployeeDashboardController(JwtUtil jwtUtil, EmployeeDashboardService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @GetMapping("/summary")
    public EmployeeDashboardResponse getSummary(
            @RequestHeader("Authorization")String authHeader
            ){

        UUID employeeId=jwtUtil.extractEmployeeId(authHeader);
        return service.getSummary(employeeId);

    }
}
