package com.employee.management.service;

import com.employee.management.entity.Attendance;
import com.employee.management.enums.AttendanceEnum;
import com.employee.management.repository.AttendanceRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class AttendanceService {
    private final AttendanceRepo attendanceRepo;

    private static final double REQUIRED_HOURS = 8.0;
    private static final double FIXED_BREAK_HOURS = 1.0;

    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo= attendanceRepo;
    }

    public String markIn(UUID employeeId) {

        LocalDate today = LocalDate.now();

        if (isWeekend(today)) {
            return "Attendance not required on weekends";
        }

        Attendance attendance = attendanceRepo
                .findByEmployeeIdAndDate(employeeId, today)
                .orElseGet(() -> {
                    Attendance a = new Attendance();
                    a.setEmployeeId(employeeId);
                    a.setDate(today);
                    a.setAttendance(AttendanceEnum.PRESENT);
                    return a;
                });

        // check ON_LEAVE
        if (attendance != null && attendance.getAttendance() == AttendanceEnum.ON_LEAVE) {
            return "Employee is on leave today";
        }

        // check already marked IN
        if (attendance != null && attendance.getInTime() != null) {
            return "In-time already marked";
        }

        // create new attendance if not exists
        if (attendance == null) {
            attendance = new Attendance();
            attendance.setEmployeeId(employeeId);
            attendance.setDate(today);
            attendance.setBreakHour(FIXED_BREAK_HOURS);
        }

        attendance.setInTime(LocalTime.now());
        attendanceRepo.save(attendance);

        return "Attendance marked IN "+LocalTime.now();
    }

    public String markOut(UUID employeeId) {

        LocalDate today = LocalDate.now();


        if (isWeekend(today)) {
            return "Attendance not required on weekends";
        }


        Attendance attendance = attendanceRepo
                .findByEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new RuntimeException("In-time not marked"));


        if (attendance.getOutTime() != null) {
            return "Out-time already marked";
        }


        if (attendance.getInTime() == null) {
            return "In-time not marked";
        }

        LocalTime outTime = LocalTime.now();


        if (outTime.isBefore(attendance.getInTime())) {
            return "Invalid out-time";
        }


        attendance.setOutTime(outTime);


        double workingHours = calculateWorkingHours(
                attendance.getInTime(),
                outTime,
                attendance.getBreakHour()   // safe even if null
        );

        attendance.setWorkingHours(workingHours);


        if (workingHours < REQUIRED_HOURS) {
            attendance.setAttendance(AttendanceEnum.HALF_DAY);
        } else {
            attendance.setAttendance(AttendanceEnum.PRESENT);
        }

        attendanceRepo.save(attendance);

        return "Out time marked. Working hours: " + workingHours;
    }


/*
    HELPERS
*/

    private Double calculateWorkingHours(
            LocalTime inTime,
            LocalTime outTime,
            Double breakHours
    ){
        long totalMinutes= Duration.between(inTime,outTime).toMinutes();
        Double safeBreakHours = breakHours == null ? 0.0 : breakHours;
        long breakMinutes = (long) (safeBreakHours * 60);

        long netMinutes=totalMinutes-breakMinutes;

        if (netMinutes < 0){
            netMinutes=0;
        }
        return netMinutes /60.0;
    }

    private boolean isWeekend(LocalDate date){
        return date.getDayOfWeek()== DayOfWeek.SATURDAY ||
                date.getDayOfWeek()== DayOfWeek.SUNDAY ;
    }

}
