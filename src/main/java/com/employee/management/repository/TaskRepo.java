package com.employee.management.repository;

import com.employee.management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepo extends JpaRepository<Task, UUID> {

    List<Task> findByAssignedTo_EmployeeIdAndDeletedFalse(UUID employeeId);

    Optional<Task> findByIdAndDeletedFalse(UUID id);
}
