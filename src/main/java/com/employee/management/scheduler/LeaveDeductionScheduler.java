package com.employee.management.scheduler;

import com.employee.management.entity.Attendance;
import com.employee.management.entity.Employee;
import com.employee.management.entity.LeaveRequest;
import com.employee.management.enums.AttendanceEnum;
import com.employee.management.repository.AttendanceRepo;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.LeaveRequestRepo;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class LeaveDeductionScheduler {
    private static final LocalTime OFFICE_END_TIME= LocalTime.of(20,0);

    private final EmployeeRepo employeeRepo;
    private final LeaveRequestRepo leaveRequestRepo;
    private final AttendanceRepo attendanceRepo;

    public LeaveDeductionScheduler(EmployeeRepo employeeRepo, LeaveRequestRepo leaveRequestRepo, AttendanceRepo attendanceRepo) {
        this.employeeRepo = employeeRepo;
        this.leaveRequestRepo = leaveRequestRepo;
        this.attendanceRepo = attendanceRepo;
    }

    @Transactional
    @Scheduled(cron = "0 5 20 * * *")
    public void deductLeavesAutomatically(){

        LocalDate today=LocalDate.now();

        if (LocalTime.now().isBefore(OFFICE_END_TIME)){
            return;
        }

       List <LeaveRequest> leaves=leaveRequestRepo.findApprovedLeavesForDate(today);

        for (LeaveRequest leave:leaves){
            if (leave.getDeductedDays()>=leave.getTotalDays()){
                continue;
            }

            Employee emp=employeeRepo.findByEmployeeIdAndDeletedFalse(leave.getEmployeeId())
                    .orElseThrow();

            Attendance attendance = attendanceRepo
                    .findByEmployeeIdAndDate(emp.getEmployeeId(), today)
                    .orElseGet(() -> {
                        Attendance a = new Attendance();
                        a.setEmployeeId(emp.getEmployeeId());
                        a.setDate(today);
                        a.setAttendance(AttendanceEnum.ON_LEAVE);
                        return a;
                    });

            if (attendance.getAttendance()==AttendanceEnum.PRESENT ||
                attendance.getAttendance()==AttendanceEnum.HALF_DAY ){
                continue;
            }

            attendance.setAttendance(AttendanceEnum.ON_LEAVE);
            attendanceRepo.save(attendance);

            emp.setLeaveBalance(emp.getLeaveBalance()-1);
            employeeRepo.save(emp);

            leave.setDeductedDays(leave.getDeductedDays()+1);
            leaveRequestRepo.save(leave);

        }
    }
}
