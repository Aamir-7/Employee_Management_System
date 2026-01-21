package com.employee.management.repository;

import com.employee.management.entity.Employee;
import com.employee.management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task>findByAssignedToAndDeletedFalse(Employee employee);
    Optional<Task>findByIdAndDeletedFalse(Long id);
}
