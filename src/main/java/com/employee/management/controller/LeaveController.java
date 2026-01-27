package com.employee.management.controller;

import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.LeaveStatus;
import com.employee.management.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    // APPLY LEAVE
    @PostMapping("/employee/{employeeId}")
    public LeaveRequest applyLeave(
            @PathVariable UUID employeeId,
            @RequestBody Map<String, String> body
    ) {
        return service.applyLeave(
                employeeId,
                body.get("reason"),
                body.get("description"),
                LocalDate.parse(body.get("startDate")),
                LocalDate.parse(body.get("endDate"))
        );
    }

    // GET LEAVES BY STATUS
    @GetMapping
    public List<LeaveRequest> getLeaves(
            @RequestParam(required = false) LeaveStatus status
    ) {
        return status == null
                ? service.getAllLeaves()
                : service.getLeavesByStatus(status);
    }

    // MANAGER → VIEW PENDING
    @GetMapping("/manager/{managerId}")
    public List<LeaveRequest> pendingLeaves(@PathVariable UUID managerId) {
        return service.getPendingLeaves(managerId);
    }

    //employee cancel leave
    @PutMapping("/{leaveId}/cancel")
    public LeaveRequest cancelLeave(@PathVariable UUID leaveId){
        return service.cancelLeave(leaveId);
    }

    // APPROVE
    @PutMapping("/{leaveId}/approve")
    public LeaveRequest approveLeave(
            @PathVariable UUID leaveId,
            @RequestHeader("Authorization") String authHeader
    ) {
        return service.approveLeave(leaveId, authHeader);
    }

    //deduct leave
    @PutMapping("/{leaveId}/deduct")
    public String deductLeave(
            @PathVariable UUID leaveId,
            @RequestHeader("Authorization") String authHeader
    ){
      return service.deductLeave(leaveId,authHeader);
    }

    // REJECT
    @PutMapping("/{leaveId}/reject")
    public LeaveRequest rejectLeave(
            @PathVariable UUID leaveId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) Map<String, String> body
    ) {
        String reason = body != null ? body.get("description") : null;
        return service.rejectLeave(leaveId, authHeader, reason);
    }
}
