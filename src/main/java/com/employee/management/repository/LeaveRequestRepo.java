package com.employee.management.repository;

import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest,Long> {
    List<LeaveRequest>findByManagerIdAndStatus(Long managerId, LeaveStatus status);

    List<LeaveRequest> findByStatus(LeaveStatus status);
}
