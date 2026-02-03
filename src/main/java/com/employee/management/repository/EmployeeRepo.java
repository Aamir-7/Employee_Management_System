package com.employee.management.repository;

import com.employee.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepo extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByWorkEmailAndDeletedFalse(String workEmail);

    Optional<Employee> findByEmployeeIdAndDeletedFalse(UUID employeeId);

    List<Employee> findByDeletedFalse();

    long count();

    long countByActiveTrue();

    long countByDeletedFalse();

    long countByManagerIdIsNull();

    List<Employee>findByManagerId(UUID mangerId);

}

