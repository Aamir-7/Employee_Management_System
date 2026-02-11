package com.employee.management.controller;

import com.employee.management.dto.EmployeeCreateRequest;
import com.employee.management.dto.EmployeeResponse;
import com.employee.management.entity.Employee;
import com.employee.management.service.EmployeeService;
import com.employee.management.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final JwtUtil jwtUtil;

    public EmployeeController(EmployeeService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // PUBLIC → get all employees
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    // PUBLIC → get employee by UUID
    @GetMapping("/{employeeId}")
    public Employee getById(@PathVariable UUID employeeId) {
        return service.getById(employeeId);
    }

    // ADMIN → create employee
    @PostMapping
    public EmployeeResponse create(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EmployeeCreateRequest request
    ) {
        jwtUtil.enforceAdmin(authHeader);
        return service.create(request);
    }

    // ADMIN → update employee
    @PutMapping("/{employeeId}")
    public Employee updateEmployee(
            @PathVariable UUID employeeId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Employee employee
    ) {
        jwtUtil.enforceAdmin(authHeader);
        return service.updateEmployee(employeeId, employee);
    }

    // ADMIN → partial update
    @PatchMapping("/{employeeId}")
    public Employee patchEmployee(
            @PathVariable UUID employeeId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Employee employee
    ) {
        jwtUtil.enforceAdmin(authHeader);
        return service.patchEmployee(employeeId, employee);
    }

    // ADMIN → soft delete
    @DeleteMapping("/{employeeId}")
    public String deleteById(
            @PathVariable UUID employeeId,
            @RequestHeader("Authorization") String authHeader
    ) {
        jwtUtil.enforceAdmin(authHeader);
        service.deleteById(employeeId);
        return "Deleted Successfully";
    }
}
