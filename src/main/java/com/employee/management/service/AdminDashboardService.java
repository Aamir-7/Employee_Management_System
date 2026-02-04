package com.employee.management.service;

import com.employee.management.dto.AdminDashboardResponse;
import com.employee.management.enums.AttendanceEnum;
import com.employee.management.enums.LeaveStatus;
import com.employee.management.enums.TaskStatus;
import com.employee.management.repository.AttendanceRepo;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.LeaveRequestRepo;
import com.employee.management.repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminDashboardService {

    private final AttendanceRepo attendanceRepo;
    private final EmployeeRepo employeeRepo;
    private final LeaveRequestRepo leaveRequestRepo;
    private final TaskRepo taskRepo;

    public AdminDashboardService(AttendanceRepo attendanceRepo, EmployeeRepo employeeRepo, LeaveRequestRepo leaveRequestRepo, TaskRepo taskRepo) {
        this.attendanceRepo = attendanceRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRequestRepo = leaveRequestRepo;
        this.taskRepo = taskRepo;
    }

    public AdminDashboardResponse getSummary() {

        long totalEmployees=employeeRepo.countByDeletedFalse();
        long totalPendingLeaves=leaveRequestRepo.countByStatus(LeaveStatus.PENDING);
        long onLeaveToday=attendanceRepo.countByDateAndAttendance(
                LocalDate.now(),
                AttendanceEnum.ON_LEAVE
        );
        long presentToday=attendanceRepo.countByDateAndAttendance(
                LocalDate.now(),
                AttendanceEnum.PRESENT
        );

        long absentToday=attendanceRepo.countByDateAndAttendance(
                LocalDate.now(),
                AttendanceEnum.ABSENT
        );

        long halfDayToday=attendanceRepo.countByDateAndAttendance(
                LocalDate.now(),
                AttendanceEnum.HALF_DAY
        );


        long employeesWithoutManager=employeeRepo.countByManagerIdIsNull();

        long totalPendingTasks=taskRepo.countByStatusAndDeletedFalse(TaskStatus.TODO);

        return new AdminDashboardResponse(
                totalEmployees,
                totalPendingLeaves,
                onLeaveToday,
                presentToday,
                absentToday,
                halfDayToday,
                employeesWithoutManager,
                totalPendingTasks
        );

    }
}
