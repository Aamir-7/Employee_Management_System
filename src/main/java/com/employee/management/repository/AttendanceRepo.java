package com.employee.management.repository;

import com.employee.management.entity.Attendance;
import com.employee.management.enums.AttendanceEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepo extends JpaRepository<Attendance, UUID> {

    long countByDateAndAttendance(
            LocalDate date,
            AttendanceEnum attendance
    );

    Optional<Attendance> findByEmployeeIdAndDate(
            UUID employeeId,
            LocalDate date
    );

    List<Attendance>findByDate(LocalDate date);

    long countByEmployeeIdAndAttendance(
            UUID employeeId,
            AttendanceEnum attendance
    );
}

