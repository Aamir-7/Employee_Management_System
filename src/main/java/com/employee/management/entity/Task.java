package com.employee.management.entity;

import com.employee.management.enums.Priority;
import com.employee.management.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate assignDate;
    private String project;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private Employee assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by", nullable = false)
    private Employee assignedBy;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    private boolean deleted = false;

    public UUID getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Employee getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Employee assignedTo) { this.assignedTo = assignedTo; }

    public Employee getAssignedBy() { return assignedBy; }
    public void setAssignedBy(Employee assignedBy) { this.assignedBy = assignedBy; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public LocalDate getAssignDate() { return assignDate; }
    public void setAssignDate(LocalDate assignDate) { this.assignDate = assignDate; }

    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
