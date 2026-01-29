package com.employee.management.repository;

import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByManagerIdAndStatus(UUID managerId, LeaveStatus status);

    List<LeaveRequest> findByStatus(LeaveStatus status);

    @Query("""
    SELECT l FROM LeaveRequest l
    WHERE l.status = 'APPROVED'
    AND :today BETWEEN l.startDate AND l.endDate
""")
    List<LeaveRequest>findApprovedLeavesForDate(LocalDate today);
}
