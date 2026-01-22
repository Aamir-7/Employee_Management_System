package com.employee.management.repository;

import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByManagerIdAndStatus(UUID managerId, LeaveStatus status);

    List<LeaveRequest> findByStatus(LeaveStatus status);
}
