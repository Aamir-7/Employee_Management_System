package com.employee.management.dto;

import com.employee.management.enums.AttendanceEnum;

import java.util.UUID;

public class EmployeeDashboardResponse {

    private UUID employeeId;
    private String name;

    private Integer leaveBalance;
    private long pendingLeaves;
    private long totalTasks;
    private AttendanceEnum todayAttendance;
    private Double performance;
    private long pendingTasks;


    public EmployeeDashboardResponse(
            UUID employeeId,
            String name,
            Integer leaveBalance,
            long pendingLeaves,
            long totalTasks,
            AttendanceEnum todayAttendance,
            Double performanceRating,
            long pendingTasks
    ) {
        this.employeeId = employeeId;
        this.name = name;
        this.leaveBalance = leaveBalance;
        this.pendingLeaves = pendingLeaves;
        this.totalTasks = totalTasks;
        this.todayAttendance = todayAttendance;
        this.performance = performanceRating;
        this.pendingTasks=pendingTasks;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public Integer getLeaveBalance() {
        return leaveBalance;
    }

    public long getPendingLeaves() {
        return pendingLeaves;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public AttendanceEnum getTodayAttendance() {
        return todayAttendance;
    }

    public Double getPerformance() {
        return performance;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }
}
