package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepo repo;

    public EmployeeService(EmployeeRepo repo) {
        this.repo = repo;
    }

    public List<Employee> getAll() {
        return repo.findByDeletedFalse();
    }

    public Employee getById(Long id) {
        return repo.findByIdAndDeletedFalse(id)
                .orElseThrow(()->new RuntimeException("Employee not found"));
    }


    public Employee create(Employee employee) {
        if (employee.getLeaveBalance() == null) {
            employee.setLeaveBalance(20);
        }
        return repo.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmp = repo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmp.setUsername(updatedEmployee.getUsername());
        existingEmp.setEmail(updatedEmployee.getEmail());
        existingEmp.setPassword(updatedEmployee.getPassword());
        existingEmp.setRole(updatedEmployee.getRole());

        existingEmp.setMobileNum(updatedEmployee.getMobileNum());
        existingEmp.setEducation(updatedEmployee.getEducation());
        existingEmp.setJobTitle(updatedEmployee.getJobTitle());
        existingEmp.setGender(updatedEmployee.getGender());
        existingEmp.setDepartment(updatedEmployee.getDepartment());
        existingEmp.setWorkMode(updatedEmployee.getWorkMode());

        existingEmp.setDateOfBirth(updatedEmployee.getDateOfBirth());
        existingEmp.setAddress(updatedEmployee.getAddress());
        existingEmp.setSalary(updatedEmployee.getSalary());
        existingEmp.setPerformance(updatedEmployee.getPerformance());
        existingEmp.setInTime(updatedEmployee.getInTime());
        existingEmp.setOutTime(updatedEmployee.getOutTime());
        existingEmp.setWorkingHours(updatedEmployee.getWorkingHours());
        existingEmp.setWorkStatus(updatedEmployee.getWorkStatus());
        existingEmp.setTask(updatedEmployee.getTask());

        return repo.save(existingEmp);

    }

    public Employee pathcEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmp = repo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (updatedEmployee.getUsername() != null)
            existingEmp.setUsername(updatedEmployee.getUsername());

        if (updatedEmployee.getEmail() != null)
            existingEmp.setEmail(updatedEmployee.getEmail());

        if (updatedEmployee.getPassword() != null)
            existingEmp.setPassword(updatedEmployee.getPassword());

        if (updatedEmployee.getRole() != null)
            existingEmp.setRole(updatedEmployee.getRole());

        if (updatedEmployee.getMobileNum() != null)
            existingEmp.setMobileNum(updatedEmployee.getMobileNum());

        if (updatedEmployee.getEducation() != null)
            existingEmp.setEducation(updatedEmployee.getEducation());

        if (updatedEmployee.getJobTitle() != null)
            existingEmp.setJobTitle(updatedEmployee.getJobTitle());

        if (updatedEmployee.getGender() != null)
            existingEmp.setGender(updatedEmployee.getGender());

        if (updatedEmployee.getDepartment() != null)
            existingEmp.setDepartment(updatedEmployee.getDepartment());

        if (updatedEmployee.getWorkMode() != null)
            existingEmp.setWorkMode(updatedEmployee.getWorkMode());

        if (updatedEmployee.getDateOfBirth() != null)
            existingEmp.setDateOfBirth(updatedEmployee.getDateOfBirth());

        if (updatedEmployee.getAddress() != null)
            existingEmp.setAddress(updatedEmployee.getAddress());

        if (updatedEmployee.getSalary() != null)
            existingEmp.setSalary(updatedEmployee.getSalary());

        if (updatedEmployee.getPerformance() != null)
            existingEmp.setPerformance(updatedEmployee.getPerformance());

        if (updatedEmployee.getInTime() != null)
            existingEmp.setInTime(updatedEmployee.getInTime());

        if (updatedEmployee.getOutTime() != null)
            existingEmp.setOutTime(updatedEmployee.getOutTime());

        if (updatedEmployee.getWorkingHours() != null)
            existingEmp.setWorkingHours(updatedEmployee.getWorkingHours());

        if (updatedEmployee.getWorkStatus() != null)
            existingEmp.setWorkStatus(updatedEmployee.getWorkStatus());

        if (updatedEmployee.getTask() != null)
            existingEmp.setTask(updatedEmployee.getTask());

        return repo.save(existingEmp);
    }

    public void deleteById(Long id) {
        Employee emp=repo.findByIdAndDeletedFalse(id)
                .orElseThrow(()->new RuntimeException("not found"));
        emp.setDeleted(true);
        repo.save(emp);
    }
}
