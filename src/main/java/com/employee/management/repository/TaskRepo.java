package com.employee.management.repository;

import com.employee.management.entity.Employee;
import com.employee.management.entity.Task;
import com.employee.management.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepo extends JpaRepository<Task, UUID> {

    List<Task> findByAssignedTo_EmployeeIdAndDeletedFalse(UUID employeeId);

    Optional<Task> findByIdAndDeletedFalse(UUID id);

    long countByStatus(TaskStatus status);

    long countByAssignedToAndDeletedFalse(Employee employee);

    long countByAssignedToAndStatus(
            Employee employee,
            TaskStatus status
    );

    long countByStatusAndDeletedFalse(TaskStatus status);


    long countByAssignedToAndStatusAndDeletedFalse(Employee employee,TaskStatus status);

    List<Task>findByAssignedTo(Employee employee);
}
