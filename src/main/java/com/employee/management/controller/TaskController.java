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

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;
    private final JwtUtil jwtUtil;

    public TaskController(TaskService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    //create task
    @PostMapping
    public TaskResponse createTask(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> body
    ) {
        // allow ADMIN or MANAGER
        jwtUtil.enforceAdminOrManager(authHeader);

        // extract creator (manager/admin) username from JWT
        String assignedBy = jwtUtil
                .extractClaims(authHeader.replace("Bearer ", "").trim())
                .getSubject();

        return service.createTask(
                body.get("title"),
                body.get("description"),
                body.get("assignedTo"),
                assignedBy,
                Priority.valueOf(body.get("priority")),
                body.get("notes"),
                LocalDate.parse(body.get("assignDate")),
                body.get("project")
        );
    }


    //Get all tasks will be update with query param later
    @GetMapping
    public List<TaskResponse>getAllTasks(@RequestHeader("Authorization")String authHeader){
        jwtUtil.enforceAdminOrManager(authHeader);
        return service.getAllTasks();
    }

    //get employees tasks
    @GetMapping("/my")
    public List<TaskResponse>getMyTasks(
            @RequestHeader("Authorization")String authHeader,
                            HttpServletRequest request){

        String token=authHeader.replace("Bearer ","").trim();
        Claims claims = jwtUtil.extractClaims(token);
        String username=claims.getSubject();

        return service.getMyTasks(username);
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateStatus(
            @PathVariable Long taskId,
            @RequestBody Map<String,String>body
    ){
        return service.updateTaskStatus(
                taskId,
                TaskStatus.valueOf(body.get("status"))
        );
    }
}
