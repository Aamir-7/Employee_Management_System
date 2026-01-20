package com.employee.management.repository;

import com.employee.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    List<Employee> findByDeletedFalse();
    Optional<Employee> findByIdAndDeletedFalse(Long id);
    Optional<Employee> findByUsernameAndDeletedFalse(String username);
    Optional<Employee> findByUsername(String username);
}
