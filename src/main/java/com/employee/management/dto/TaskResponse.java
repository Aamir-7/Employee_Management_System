package com.employee.management.dto;

import com.employee.management.enums.Priority;
import com.employee.management.enums.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

public class TaskResponse {

    private UUID id;
    private String title;
    private String description;

    private UUID assignedToId;
    private UUID assignedById;

    private TaskStatus status;
    private Priority priority;
    private String notes;
    private LocalDate assignDate;
    private String project;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getAssignedToId() { return assignedToId; }
    public void setAssignedToId(UUID assignedToId) { this.assignedToId = assignedToId; }

    public UUID getAssignedById() { return assignedById; }
    public void setAssignedById(UUID assignedById) { this.assignedById = assignedById; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getAssignDate() { return assignDate; }
    public void setAssignDate(LocalDate assignDate) { this.assignDate = assignDate; }

    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }
}
