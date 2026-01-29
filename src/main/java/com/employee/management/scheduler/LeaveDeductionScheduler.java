package com.employee.management.scheduler;

import com.employee.management.entity.Employee;
import com.employee.management.entity.LeaveRequest;
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

    public LeaveDeductionScheduler(EmployeeRepo employeeRepo, LeaveRequestRepo leaveRequestRepo) {
        this.employeeRepo = employeeRepo;
        this.leaveRequestRepo = leaveRequestRepo;
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

            emp.setLeaveBalance(emp.getLeaveBalance()-1);
            employeeRepo.save(emp);

            leave.setDeductedDays(leave.getDeductedDays()+1);
            leaveRequestRepo.save(leave);
        }
    }
}
