package com.employee.management.controller;

import com.employee.management.service.EmployeeFileService;
import com.employee.management.util.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeFileController {

    private final EmployeeFileService fileService;
    private final JwtUtil jwtUtil;

    public EmployeeFileController(EmployeeFileService fileService, JwtUtil jwtUtil) {
        this.fileService = fileService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{employeeId}/photo")
    public String uploadPhoto(
            @PathVariable UUID employeeId,
            @RequestParam MultipartFile file,
            @RequestHeader("Authorization") String authHeader
            ){
        jwtUtil.enforceAdminOrSelf(authHeader, employeeId);
        return fileService.uploadPhoto(
                employeeId,
                file
        );
    }

    @PostMapping("/{employeeId}/resume")
    public String uploadResume(
            @PathVariable UUID employeeId,
            @RequestParam MultipartFile file,
            @RequestHeader("Authorization") String authHeader
    ){
        jwtUtil.enforceAdminOrSelf(authHeader, employeeId);
        return fileService.uploadResume(
                employeeId,
                file
        );
    }
}
