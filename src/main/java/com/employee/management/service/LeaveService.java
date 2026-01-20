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

import java.util.List;

@Service
public class LeaveService {

    private final EmployeeRepo employeeRepo;
    private final LeaveRequestRepo leaveRepo;
    private final JwtUtil jwtUtil;

    public LeaveService(EmployeeRepo employeeRepo, LeaveRequestRepo leaveRepo, JwtUtil jwtUtil) {
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.jwtUtil = jwtUtil;
    }

    public LeaveRequest applyLeave(Long employeeId, String reason, String description) {

        Employee emp=employeeRepo.findById(employeeId)
                .orElseThrow(()->new RuntimeException("employee not found"));

        LeaveRequest leave=new LeaveRequest();
        leave.setEmployeeId(employeeId);
        leave.setManagerId(emp.getManager());
        leave.setReason(reason);
        leave.setDescription(description);
        leave.setStatus(LeaveStatus.PENDING);
        return leaveRepo.save(leave);
    }

    public List<LeaveRequest> getpendingLeaves(Long managerId) {
        return leaveRepo.findByManagerIdAndStatus(managerId,LeaveStatus.PENDING);

    }
    @Transactional
    public LeaveRequest approveLeave(Long leaveId, String authHeader) {

        // extract token
        String token = authHeader.replace("Bearer ", "").trim();
        Claims claims = jwtUtil.extractClaims(token);

        String approverUsername = claims.getSubject();
        String role = claims.get("role", String.class);
        boolean isAdmin = "ADMIN".equals(role);

        // fetch leave
        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        // must be pending
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave already processed");
        }

        // fetch approver
        Employee approver = employeeRepo.findByUsername(approverUsername)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        // authorization: manager of employee OR admin
        if (!isAdmin && !leave.getManagerId().equals(approver.getId())) {
            throw new RuntimeException("Not authorized to approve this leave");
        }

        // fetch employee
        Employee emp = employeeRepo.findById(leave.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // leave balance check (IMPORTANT)
        if (emp.getLeaveBalance() <= 0) {
            throw new RuntimeException("No leave balance available");
        }

        // deduct leave
        emp.setLeaveBalance(emp.getLeaveBalance() - 1);
        employeeRepo.save(emp);

        // approve leave
        leave.setStatus(LeaveStatus.APPROVED);
        return leaveRepo.save(leave);
    }

    @Transactional
    public LeaveRequest rejectLeave(Long leaveId, String authHeader, String rejectReason) {

        String token=authHeader.replace("Bearer ","").trim();
        Claims claims=jwtUtil.extractClaims(token);

        String approverUsername=claims.getSubject();
        String role=claims.get("role", String.class);
        boolean isAdmin="ADMIN".equals(role);

        //fetch leave
        LeaveRequest leave=leaveRepo.findById(leaveId)
                .orElseThrow(()->new RuntimeException("leave not found"));

        //check pending
        if (leave.getStatus()!=LeaveStatus.PENDING){
            throw new RuntimeException("leave already processed");
        }

        //fetch approver
        Employee approver=employeeRepo.findByUsername(approverUsername)
                .orElseThrow(()->new RuntimeException("approver not found"));

        //check for admin or manager
        if (!isAdmin && !leave.getManagerId().equals(approver.getId())){
            throw new RuntimeException("approver not authorized to process the request");
        }
        if (rejectReason!=null && !rejectReason.isBlank()){
            leave.setDescription(rejectReason);
        }
        leave.setStatus(LeaveStatus.REJECTED);
        return leaveRepo.save(leave);
    }

    //get all leaves
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAll();
    }

    public List<LeaveRequest> getLeavesByStatus(LeaveStatus status) {
        return leaveRepo.findByStatus(status);
    }
}
