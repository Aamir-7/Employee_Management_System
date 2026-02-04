package com.employee.management.dto;

public class AdminDashboardResponse {

    private long totalEmployees;
    private long pendingLeaves;
    private long onLeaveToday;
    private long presentToday;
    private long absentToday;
    private long halfDayToday;
    private long employeesWithoutManager;
    private long totalPendingTasks;


    public AdminDashboardResponse(
            long totalEmployees,
            long pendingLeaves,
            long onLeaveToday,
            long presentToday,
            long absentToday,
            long halfDayToday,
            long employeesWithoutManager,
            long totalPendingTasks
    ) {
        this.totalEmployees = totalEmployees;
        this.pendingLeaves = pendingLeaves;
        this.onLeaveToday = onLeaveToday;
        this.presentToday = presentToday;
        this.absentToday = absentToday;
        this.halfDayToday = halfDayToday;
        this.employeesWithoutManager=employeesWithoutManager;
        this.totalPendingTasks =totalPendingTasks;

    }

    public long getHalfDayToday() {
        return halfDayToday;
    }

    public long getAbsentToday() {
        return absentToday;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public long getPendingLeaves() {
        return pendingLeaves;
    }

    public long getOnLeaveToday() {
        return onLeaveToday;
    }

    public long getPresentToday() {
        return presentToday;
    }

    public long getEmployeesWithoutManager() {
        return employeesWithoutManager;
    }

    public long getTotalPendingTasks() {
        return totalPendingTasks;
    }
}
