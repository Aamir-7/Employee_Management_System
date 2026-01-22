package com.employee.management.service;

import com.employee.management.dto.TaskResponse;
import com.employee.management.entity.Employee;
import com.employee.management.entity.Task;
import com.employee.management.enums.Priority;
import com.employee.management.enums.TaskStatus;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.TaskRepo;
import com.employee.management.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final EmployeeRepo employeeRepo;
    private final JwtUtil jwtUtil;

    public TaskService(TaskRepo taskRepo,
                       EmployeeRepo employeeRepo,
                       JwtUtil jwtUtil) {
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
        this.jwtUtil = jwtUtil;
    }

    public TaskResponse createTask(
            String title,
            String description,
            UUID assignedToId,
            String authHeader,
            Priority priority,
            String notes,
            LocalDate assignDate,
            String project
    ) {

        Claims claims = jwtUtil.extractClaims(
                authHeader.replace("Bearer ", "").trim()
        );

        UUID assignedById = UUID.fromString(claims.getSubject());

        Employee assignedTo = employeeRepo
                .findByEmployeeIdAndDeletedFalse(assignedToId)
                .orElseThrow(() -> new RuntimeException("Assigned employee not found"));

        Employee assignedBy = employeeRepo
                .findByEmployeeIdAndDeletedFalse(assignedById)
                .orElseThrow(() -> new RuntimeException("Assigner not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setAssignedTo(assignedTo);
        task.setAssignedBy(assignedBy);
        task.setPriority(priority);
        task.setNotes(notes);
        task.setAssignDate(assignDate);
        task.setProject(project);
        task.setStatus(TaskStatus.TODO);

        return mapToResponse(taskRepo.save(task));
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse res = new TaskResponse();
        res.setId(task.getId());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setAssignedToId(task.getAssignedTo().getEmployeeId());
        res.setAssignedById(task.getAssignedBy().getEmployeeId());
        res.setPriority(task.getPriority());
        res.setNotes(task.getNotes());
        res.setAssignDate(task.getAssignDate());
        res.setProject(task.getProject());
        res.setStatus(task.getStatus());
        return res;
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepo.findAll()
                .stream()
                .filter(t -> !t.isDeleted())
                .map(this::mapToResponse)
                .toList();
    }

    public List<TaskResponse> getMyTasks(String authHeader) {

        Claims claims = jwtUtil.extractClaims(
                authHeader.replace("Bearer ", "").trim()
        );

        UUID employeeId = UUID.fromString(claims.getSubject());

        return taskRepo.findByAssignedTo_EmployeeIdAndDeletedFalse(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse updateTaskStatus(UUID taskId, TaskStatus status) {

        Task task = taskRepo.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);
        return mapToResponse(taskRepo.save(task));
    }
}
