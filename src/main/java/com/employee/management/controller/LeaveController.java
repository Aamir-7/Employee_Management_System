package com.employee.management.controller;

import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.LeaveStatus;
import com.employee.management.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    //apply leave
    @PostMapping("/employee/{id}")
    public LeaveRequest applyLeave(
            @PathVariable Long id,
            @RequestBody Map<String,String>body
            ){
        return service.applyLeave(
                id,
                body.get("reason"),
                body.get("description"),
                LocalDate.parse(body.get("startDate")),
                LocalDate.parse(body.get("endDate"))
        );
    }

    //get leaves by status
    @GetMapping
    public List<LeaveRequest>getLeaves(
            @RequestParam(required = false)LeaveStatus status
            ){

        if (status==null){
            return service.getAllLeaves();
        }

        return service.getLeavesByStatus(status);
    }

    //get pending leaves by manager
    @GetMapping("/manager/{id}")
    public List<LeaveRequest>pendingLeaves(@PathVariable Long id){
        return service.getpendingLeaves(id);
    }

    //Approve
    @PutMapping("/{leaveId}/approve")
    public LeaveRequest approveLeave(
            @PathVariable Long leaveId,
            @RequestHeader("Authorization") String authHeader
    ) {
        return service.approveLeave(leaveId, authHeader);
    }

    //Reject
    @PutMapping("/{leaveId}/reject")
    public LeaveRequest rejectLeave(
            @PathVariable Long leaveId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false)Map<String,String>body
    ){
        String rejectReason=null;
        if (body!=null){
            rejectReason=body.get("description");

        }
        return service.rejectLeave(leaveId,authHeader,rejectReason);
    }
}

