package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.LeaveStatus;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.LeaveRequestRepo;
import com.employee.management.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class LeaveService {

    private final EmployeeRepo employeeRepo;
    private final LeaveRequestRepo leaveRepo;
    private final JwtUtil jwtUtil;

    public LeaveService(EmployeeRepo employeeRepo,
                        LeaveRequestRepo leaveRepo,
                        JwtUtil jwtUtil) {
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.jwtUtil = jwtUtil;
    }

    /* ======================
       APPLY LEAVE
       ====================== */
    public LeaveRequest applyLeave(
            UUID employeeId,
            String reason,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("End date cannot be before start date");
        }

        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        Employee emp = employeeRepo
                .findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployeeId(employeeId);
        leave.setManagerId(emp.getManagerId());
        leave.setReason(reason);
        leave.setDescription(description);
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setTotalDays(totalDays);
        leave.setStatus(LeaveStatus.PENDING);

        return leaveRepo.save(leave);
    }

    /* ======================
       MANAGER → VIEW PENDING
       ====================== */
    public List<LeaveRequest> getPendingLeaves(UUID managerId) {
        return leaveRepo.findByManagerIdAndStatus(managerId, LeaveStatus.PENDING);
    }

    /* ======================
       APPROVE LEAVE
       ====================== */
    @Transactional
    public LeaveRequest approveLeave(UUID leaveId, String authHeader) {

        Claims claims = jwtUtil.extractClaims(
                authHeader.replace("Bearer ", "").trim()
        );

        UUID approverId = UUID.fromString(claims.getSubject());
        boolean isAdmin = "ADMIN".equals(claims.get("role", String.class));

        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave already processed");
        }

        if (!isAdmin && !leave.getManagerId().equals(approverId)) {
            throw new RuntimeException("Not authorized to approve");
        }

        Employee emp = employeeRepo
                .findByEmployeeIdAndDeletedFalse(leave.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getLeaveBalance() < leave.getTotalDays()) {
            throw new RuntimeException("Insufficient leave balance");
        }

        emp.setLeaveBalance(emp.getLeaveBalance() - leave.getTotalDays());
        employeeRepo.save(emp);

        leave.setStatus(LeaveStatus.APPROVED);
        return leaveRepo.save(leave);
    }

    /* ======================
       REJECT LEAVE
       ====================== */
    @Transactional
    public LeaveRequest rejectLeave(UUID leaveId,
                                    String authHeader,
                                    String rejectReason) {

        Claims claims = jwtUtil.extractClaims(
                authHeader.replace("Bearer ", "").trim()
        );

        UUID approverId = UUID.fromString(claims.getSubject());
        boolean isAdmin = "ADMIN".equals(claims.get("role", String.class));

        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave already processed");
        }

        if (!isAdmin && !leave.getManagerId().equals(approverId)) {
            throw new RuntimeException("Not authorized");
        }

        if (rejectReason != null && !rejectReason.isBlank()) {
            leave.setDescription(rejectReason);
        }

        leave.setStatus(LeaveStatus.REJECTED);
        return leaveRepo.save(leave);
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAll();
    }

    public List<LeaveRequest> getLeavesByStatus(LeaveStatus status) {
        return leaveRepo.findByStatus(status);
    }
}
