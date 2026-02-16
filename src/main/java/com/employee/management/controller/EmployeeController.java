package com.employee.management.controller;

import com.employee.management.dto.EmployeeRequestDTO;
import com.employee.management.dto.EmployeeResponseDTO;
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
    public List<EmployeeResponseDTO> getAll() {
        return service.getAll();
    }

    // PUBLIC → get employee by UUID
    @GetMapping("/{employeeId}")
    public EmployeeResponseDTO getById(@PathVariable UUID employeeId) {
        return service.getById(employeeId);
    }

    // ADMIN → create employee
    @PostMapping
    public EmployeeResponseDTO create(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EmployeeRequestDTO request
    ) {
        jwtUtil.enforceAdmin(authHeader);
        return service.create(request);
    }

    // ADMIN → update employee
    @PutMapping("/{employeeId}")
    public EmployeeResponseDTO updateEmployee(
            @PathVariable UUID employeeId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EmployeeRequestDTO request
    ) {
        jwtUtil.enforceAdmin(authHeader);
        return service.updateEmployee(employeeId, request);
    }

    // ADMIN → partial update
    @PatchMapping("/{employeeId}")
    public EmployeeResponseDTO patchEmployee(
            @PathVariable UUID employeeId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EmployeeRequestDTO request
    ) {
        jwtUtil.enforceAdmin(authHeader);
        return service.patchEmployee(employeeId, request);
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
