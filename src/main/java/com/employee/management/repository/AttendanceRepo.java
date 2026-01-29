package com.employee.management.repository;

import com.employee.management.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepo extends JpaRepository<Attendance, UUID> {

    Optional<Attendance> findByEmployeeIdAndDate(UUID employeeId, LocalDate date);
}

