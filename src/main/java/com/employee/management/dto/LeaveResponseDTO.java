package com.employee.management.dto;

import com.employee.management.enums.LeaveStatus;

import java.time.LocalDate;
import java.util.UUID;

public class LeaveResponseDTO {

    private UUID id;
    private UUID employeeId;
    private UUID managerId;
    private String reason;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private LeaveStatus status;
    private Integer deductedDays;

    public LeaveResponseDTO() {
    }

    public LeaveResponseDTO(
            UUID id,
            UUID employeeId,
            UUID managerId,
            String reason,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            Integer totalDays,
            LeaveStatus status,
            Integer deductedDays
    ) {
        this.id = id;
        this.employeeId = employeeId;
        this.managerId = managerId;
        this.reason = reason;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.status = status;
        this.deductedDays = deductedDays;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public Integer getDeductedDays() {
        return deductedDays;
    }

    public void setDeductedDays(Integer deductedDays) {
        this.deductedDays = deductedDays;
    }
}
