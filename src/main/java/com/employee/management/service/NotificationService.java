package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.entity.LeaveRequest;
import com.employee.management.entity.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {

    private final MailService mailService;

    public NotificationService(MailService service) {
        this.mailService = service;
    }

    //                    For Leave                     //

    public void sendLeaveApplied(
            Employee emp,
            String reason,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ){
        String subject="Leave applied";
        String body=
                "Leave Application submitted.\n\n"+
                        "Reason: "+reason+"\n"+
                        "Description: "+description+"\n"+
                        "StartDate: "+startDate+"\n"+
                        "EndDate: "+endDate;

        mailService.send(
                emp.getWorkEmail(),
                subject,
                body
        );
    }

    public void sendLeaveApproved(Employee emp, LeaveRequest leave){
        mailService.send(
                emp.getWorkEmail(),
                "Leave approved",
                  "Your leave has been approved.\n "+
                        "FROM: "+leave.getStartDate()+"\n"+
                        "To: "+leave.getEndDate()
        );
    }

    public void sendLeaveCancelled(Employee emp,LeaveRequest leave){
        mailService.send(
                emp.getWorkEmail(),
                "Leave cancelled",
                "The employee has cancelled "+leave.getDescription()+" leave"
        );
    }

    public void sendLeaveRejected(Employee emp,String reason){
        mailService.send(
                emp.getWorkEmail(),
                "Leave rejected",
                "Your leave request was rejected.\n\nReason: "+reason
        );
    }

    //                  For Task                    //

    public void sendTaskAssigned(Employee emp, Task task){
        mailService.send(
                emp.getWorkEmail(),
                "New Task Assigned",
                  "You have been assigned a new task:\n\n" +
                        "Title: " + task.getTitle() + "\n" +
                        "Description: " + task.getDescription()
        );
    }

    public void sendTaskUpdated(Employee emp, Task task){
        mailService.send(
                emp.getWorkEmail(),
                "The task status has been updated",
                "The project: "+task.getProject()+" The task: "+task.getTitle()+" has been updated to: "+task.getStatus()
        );
    }
}
