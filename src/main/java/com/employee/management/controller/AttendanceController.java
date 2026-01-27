package com.employee.management.controller;

import com.employee.management.service.AttendanceService;
import com.employee.management.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final JwtUtil jwtUtil;
    private final AttendanceService attendanceService;

    public AttendanceController(JwtUtil jwtUtil, AttendanceService attendanceService) {
        this.jwtUtil = jwtUtil;
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public String markAttendance(@RequestHeader("Authorization") String authHeader){

        Claims claims= jwtUtil.extractClaims(authHeader.replace("Bearer ","").trim());
        UUID employeeId=UUID.fromString(claims.getSubject());

         attendanceService.markAttendance(
                employeeId,
                 LocalDate.now()
        );
        return "attendance marked as present "+LocalDate.now();
    }
}
