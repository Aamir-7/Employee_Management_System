package com.employee.management.controller;

import com.employee.management.dto.TaskResponse;
import com.employee.management.enums.Priority;
import com.employee.management.enums.TaskStatus;
import com.employee.management.service.TaskService;
import com.employee.management.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;
    private final JwtUtil jwtUtil;

    public TaskController(TaskService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public TaskResponse createTask(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> body
    ) {
        jwtUtil.enforceAdminOrManager(authHeader);

        return service.createTask(
                body.get("title"),
                body.get("description"),
                UUID.fromString(body.get("assignedToId")),
                authHeader,
                Priority.valueOf(body.get("priority")),
                body.get("notes"),
                LocalDate.parse(body.get("assignDate")),
                body.get("project")
        );
    }

    @GetMapping
    public List<TaskResponse> getAllTasks(
            @RequestHeader("Authorization") String authHeader
    ) {
        jwtUtil.enforceAdminOrManager(authHeader);
        return service.getAllTasks();
    }

    @GetMapping("/my")
    public List<TaskResponse> getMyTasks(
            @RequestHeader("Authorization") String authHeader
    ) {
        return service.getMyTasks(authHeader);
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateStatus(
            @PathVariable UUID taskId,
            @RequestBody Map<String, String> body
    ) {
        return service.updateTaskStatus(
                taskId,
                TaskStatus.valueOf(body.get("status"))
        );
    }
}
