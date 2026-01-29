package com.employee.management.entity;

import com.employee.management.enums.AttendanceEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "date"})
        }
)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private LocalDate date;

    private LocalTime inTime;
    private LocalTime outTime;

    private Double breakHour=1.0;
    private Double workingHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceEnum attendance;

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public Double getBreakHour() {
        return breakHour;
    }

    public void setBreakHour(Double breakHour) {
        this.breakHour = breakHour;
    }


    /* ---------- Constructors ---------- */
    public Attendance() {}

    /* ---------- Getters & Setters ---------- */
    public UUID getId() { return id; }

    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getInTime() { return inTime; }
    public void setInTime(LocalTime inTime) { this.inTime = inTime; }

    public LocalTime getOutTime() { return outTime; }
    public void setOutTime(LocalTime outTime) { this.outTime = outTime; }

    public AttendanceEnum getAttendance() { return attendance; }
    public void setAttendance(AttendanceEnum attendance) { this.attendance = attendance; }
}
