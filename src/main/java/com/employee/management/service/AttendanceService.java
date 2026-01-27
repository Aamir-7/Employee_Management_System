package com.employee.management.service;

import com.employee.management.entity.Attendance;
import com.employee.management.enums.AttendanceEnum;
import com.employee.management.repository.AttendanceRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.UUID;

@Service
public class AttendanceService {

    private final AttendanceRepo attendanceRepo;
    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public void markAttendance(UUID employeeId, LocalDate now) {

        Attendance attendance=attendanceRepo.findByEmployeeIdAndDate(employeeId,now)
                        .orElse(new Attendance());
        attendance.setEmployeeId(employeeId);
        attendance.setDate(now);
        attendance.setAttendance(AttendanceEnum.PRESENT);
        attendanceRepo.save(attendance);

    }
}
