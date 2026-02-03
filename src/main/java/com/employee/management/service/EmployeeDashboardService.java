package com.employee.management.service;

import com.employee.management.dto.EmployeeDashboardResponse;
import com.employee.management.entity.Attendance;
import com.employee.management.entity.Employee;
import com.employee.management.enums.AttendanceEnum;
import com.employee.management.enums.LeaveStatus;
import com.employee.management.enums.TaskStatus;
import com.employee.management.repository.AttendanceRepo;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.LeaveRequestRepo;
import com.employee.management.repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EmployeeDashboardService {

    private final AttendanceRepo attendanceRepo;
    private final EmployeeRepo employeeRepo;
    private final LeaveRequestRepo leaveRequestRepo;
    private final TaskRepo taskRepo;

    public EmployeeDashboardService(AttendanceRepo attendanceRepo, EmployeeRepo employeeRepo, LeaveRequestRepo leaveRequestRepo, TaskRepo taskRepo) {
        this.attendanceRepo = attendanceRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRequestRepo = leaveRequestRepo;
        this.taskRepo = taskRepo;
    }

    public EmployeeDashboardResponse getSummary(UUID employeeId) {

        Employee emp=employeeRepo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(()->new RuntimeException("employee not found"));

        long pendingLeaves=leaveRequestRepo.countByEmployeeIdAndStatus(employeeId, LeaveStatus.PENDING);

        Attendance todayAttendance=attendanceRepo.findByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElse(null);

        long totalTasks =
                taskRepo.countByAssignedToAndDeletedFalse(emp);

        Double performanceRating= emp.getPerformanceRating();

        long pendingTask=taskRepo.countByAssignedToAndStatusAndDeletedFalse(emp,TaskStatus.TODO);

        return new EmployeeDashboardResponse(
                emp.getEmployeeId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getLeaveBalance(),
                pendingLeaves,
                totalTasks,
                todayAttendance != null ? todayAttendance.getAttendance() : AttendanceEnum.ABSENT,
                performanceRating,
                pendingTask
        );
    }
}
