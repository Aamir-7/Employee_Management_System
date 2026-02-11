package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.entity.LeaveRequest;
import com.employee.management.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final SendGridEmailService emailService;

    @Value("${SENDGRID_TEMPLATE_LEAVE}")
    private String leaveTemplateId;

    private static final Logger log =
            LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(SendGridEmailService emailService) {
        this.emailService = emailService;
    }

    /* =========================
       LEAVE EMAILS
       ========================= */

    public void sendLeaveApplied(Employee applicant,
                                 Employee manager,
                                 String reason,
                                 String description,
                                 LocalDate startDate,
                                 LocalDate endDate) {

        try {
            Map<String, Object> data = new HashMap<>();

            data.put("managerName", manager.getFirstName());
            data.put("employeeName", applicant.getFirstName() + " " + applicant.getLastName());
            data.put("reason", reason);
            data.put("description", description);
            data.put("startDate", startDate.toString());
            data.put("endDate", endDate.toString());
            data.put("actionUrl", "https://your-frontend-url.com/manager/leave-requests");

            emailService.sendTemplateEmail(
                    manager.getWorkEmail(),
                    leaveTemplateId,
                    data
            );

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

