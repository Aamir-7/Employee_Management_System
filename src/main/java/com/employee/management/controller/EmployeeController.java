package com.employee.management.controller;

import com.employee.management.entity.Employee;
import com.employee.management.service.EmployeeService;
import com.employee.management.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;
    private final JwtUtil jwtUtil;

    public EmployeeController(EmployeeService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    //public
    @GetMapping
    public List<Employee>getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id){
        return service.getById(id);
    }

    //protected
    @PostMapping
    public Employee create(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Employee employee){
        jwtUtil.enforceAdmin(authHeader);
        return service.create(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Employee employee){
        jwtUtil.enforceAdmin(authHeader);
        return service.updateEmployee(id,employee);

    }

    @PatchMapping("/{id}")
    public Employee patchEmployee(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Employee employee){
        jwtUtil.enforceAdmin(authHeader);
        return service.pathcEmployee(id,employee);
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        jwtUtil.enforceAdmin(authHeader);
        service.deleteById(id);
    }

}
