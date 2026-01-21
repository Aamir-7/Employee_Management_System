package com.employee.management.service;

import com.employee.management.dto.TaskResponse;
import com.employee.management.entity.Employee;
import com.employee.management.entity.Task;
import com.employee.management.enums.Priority;
import com.employee.management.enums.TaskStatus;
import com.employee.management.repository.EmployeeRepo;
import com.employee.management.repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final EmployeeRepo employeeRepo;

    public TaskService(TaskRepo taskRepo, EmployeeRepo employeeRepo) {
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
    }

    public TaskResponse createTask(
            String title,
            String description,
            String assignedToUsername,
            String assignedByUsername,
            Priority priority,
            String notes,
            LocalDate assignDate,
            String project
    ) {

        Employee assignedTo = employeeRepo.findByUsername(assignedToUsername)
                .orElseThrow(() -> new RuntimeException("Assigned employee not found"));

        Employee assignedBy = employeeRepo.findByUsername(assignedByUsername)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

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

        Task savedTask = taskRepo.save(task);
        return mapToResponse(savedTask);
    }


    private TaskResponse mapToResponse(Task task) {

        TaskResponse res = new TaskResponse();
        res.setId(task.getId());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setAssignedToUsername(task.getAssignedTo().getUsername());
        res.setAssignedByUsername(task.getAssignedBy().getUsername());
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
                .map(this::mapToResponse)
                .toList();
    }

    public List<TaskResponse> getMyTasks(String username) {
        Employee emp=employeeRepo.findByUsernameAndDeletedFalse(username)
                .orElseThrow(()->new RuntimeException("employee not found"));

        return taskRepo.findByAssignedToAndDeletedFalse(emp)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status) {

        Task task=taskRepo.findByIdAndDeletedFalse(taskId)
                .orElseThrow(()->new RuntimeException("task not found"));
        task.setStatus(status);
        Task updatedTask=taskRepo.save(task);

        return mapToResponse(updatedTask);
    }
}
