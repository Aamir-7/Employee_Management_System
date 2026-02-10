package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.entity.LeaveRequest;
import com.employee.management.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {

    private final SendGridEmailService emailService;
    private static final Logger log =
            LoggerFactory.getLogger(NotificationService.class);


    public NotificationService(SendGridEmailService emailService) {
        this.emailService = emailService;
    }

    /* =========================
       LEAVE EMAILS
       ========================= */

    public void sendLeaveApplied(
            Employee emp,
            String reason,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) {
        try {
            String subject = "Leave Applied";
            String body =
                    "Leave application submitted.\n\n" +
                            "Reason: " + reason + "\n" +
                            "Description: " + description + "\n" +
                            "Start Date: " + startDate + "\n" +
                            "End Date: " + endDate;

            emailService.sendEmail(emp.getWorkEmail(), subject, body);
        } catch (Exception e) {
            log.error("Failed to send leave applied email", e);
        }
    }

    public void sendLeaveApproved(Employee emp, LeaveRequest leave) {
        try {
            emailService.sendEmail(
                    emp.getWorkEmail(),
                    "Leave Approved",
                    "Your leave has been approved.\n\n" +
                            "From: " + leave.getStartDate() + "\n" +
                            "To: " + leave.getEndDate()
            );
        } catch (Exception e) {
            log.error("Failed to send leave approved email", e);
        }
    }

    public void sendLeaveCancelled(Employee emp, LeaveRequest leave) {
        try {
            emailService.sendEmail(
                    emp.getWorkEmail(),
                    "Leave Cancelled",
                    "The following leave has been cancelled:\n\n" +
                            leave.getDescription()
            );
        } catch (Exception e) {
            log.error("Failed to send leave cancelled email", e);
        }
    }


    public void sendLeaveRejected(Employee emp, String reason) {
        try {
            emailService.sendEmail(
                    emp.getWorkEmail(),
                    "Leave Rejected",
                    "Your leave request was rejected.\n\nReason: " + reason
            );
        } catch (Exception e) {
            log.error("Failed to send leave rejected email", e);
        }
    }

 /* =========================
       TASK EMAILS
       ========================= */

    public void sendTaskAssigned(Employee emp, Task task) {
        try {
            emailService.sendEmail(
                    emp.getWorkEmail(),
                    "New Task Assigned",
                    "You have been assigned a new task.\n\n" +
                            "Title: " + task.getTitle() + "\n" +
                            "Description: " + task.getDescription() + "\n" +
                            "Project: " + task.getProject()
            );
        } catch (Exception e) {
            log.error("Failed to send task assigned email", e);
        }
    }

    public void sendTaskUpdated(Employee emp, Task task) {
        try {
            emailService.sendEmail(
                    emp.getWorkEmail(),
                    "Task Status Updated",
                    "The task status has been updated.\n\n" +
                            "Project: " + task.getProject() + "\n" +
                            "Task: " + task.getTitle() + "\n" +
                            "New Status: " + task.getStatus()
            );
        } catch (Exception e) {
            log.error("Failed to send task updated email", e);
        }
    }
}

