package com.employee.management.dto;

import com.employee.management.enums.AttendanceEnum;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResponseDTO {

    private LocalDate date;

    private AttendanceEnum attendanceEnum;

    private LocalTime inTime;

    private LocalTime outTime;

    private Double breakHour;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceEnum getAttendanceEnum() {
        return attendanceEnum;
    }

    public void setAttendanceEnum(AttendanceEnum attendanceEnum) {
        this.attendanceEnum = attendanceEnum;
    }

    public LocalTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalTime inTime) {
        this.inTime = inTime;
    }

    public LocalTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalTime outTime) {
        this.outTime = outTime;
    }

    public Double getBreakHour() {
        return breakHour;
    }

    public void setBreakHour(Double breakHour) {
        this.breakHour = breakHour;
    }
}
