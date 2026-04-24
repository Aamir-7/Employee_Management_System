package com.employee.management.controller;

import com.employee.management.dto.AttendanceResponseDTO;
import com.employee.management.entity.Attendance;
import com.employee.management.service.AttendanceService;
import com.employee.management.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService service;
    private final JwtUtil jwtUtil;

    public AttendanceController(AttendanceService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/in")
    public String markIn(
            @RequestHeader("Authorization") String authHeader
    ){
        Claims claims= jwtUtil.extractClaims(authHeader.replace("Bearer ","").trim());
        UUID employeeId=UUID.fromString(claims.getSubject());

        return service.markIn(employeeId);
    }

    @PostMapping("/out")
    public String markOut(
            @RequestHeader("Authorization")String authHeader
    ){
        Claims claims= jwtUtil.extractClaims(authHeader.replace("Bearer ","").trim());
        UUID employeeId=UUID.fromString(claims.getSubject());

        return service.markOut(employeeId);
    }

    @GetMapping("/my")
    public List<AttendanceResponseDTO>getMyAttendance(
            @RequestHeader("Authorization") String authHeader
    ){
        UUID empId=jwtUtil.extractEmployeeId(authHeader);
        return service.getMyAttendance(empId);

    }
}
