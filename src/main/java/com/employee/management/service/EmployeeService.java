package com.employee.management.service;

import com.employee.management.entity.Employee;
import com.employee.management.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepo repo;

    public EmployeeService(EmployeeRepo repo) {
        this.repo = repo;
    }

    /* ======================
       GET ALL (NON-DELETED)
       ====================== */
    public List<Employee> getAll() {
        return repo.findByDeletedFalse();
    }

    /* ======================
       GET BY UUID
       ====================== */
    public Employee getById(UUID employeeId) {
        return repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    /* ======================
       CREATE
       ====================== */
    public Employee create(Employee employee) {
        if (employee.getLeaveBalance() == null) {
            employee.setLeaveBalance(20);
        }
        return repo.save(employee);
    }

    /* ======================
       FULL UPDATE (PUT)
       ====================== */
    public Employee updateEmployee(UUID employeeId, Employee updated) {

        Employee existing = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setFirstName(updated.getFirstName());
        existing.setMiddleName(updated.getMiddleName());
        existing.setLastName(updated.getLastName());
        existing.setRole(updated.getRole());

        existing.setWorkEmail(updated.getWorkEmail());
        existing.setPassword(updated.getPassword());
        existing.setMicrosoftId(updated.getMicrosoftId());

        existing.setMobileNumber(updated.getMobileNumber());
        existing.setAlternatePhoneNumber(updated.getAlternatePhoneNumber());

        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setGender(updated.getGender());
        existing.setNationality(updated.getNationality());
        existing.setMaritalStatus(updated.getMaritalStatus());
        existing.setBloodGroup(updated.getBloodGroup());

        existing.setCurrentAddress(updated.getCurrentAddress());
        existing.setPermanentAddress(updated.getPermanentAddress());

        existing.setEmergencyContactName(updated.getEmergencyContactName());
        existing.setEmergencyContactNumber(updated.getEmergencyContactNumber());

        existing.setJobTitle(updated.getJobTitle());
        existing.setDepartment(updated.getDepartment());
        existing.setWorkMode(updated.getWorkMode());
        existing.setEmploymentType(updated.getEmploymentType());
        existing.setEmployeeStatus(updated.getEmployeeStatus());
        existing.setDateOfJoining(updated.getDateOfJoining());
        existing.setOfficeLocation(updated.getOfficeLocation());

        existing.setManagerId(updated.getManagerId());

        existing.setShiftTiming(updated.getShiftTiming());
        existing.setPerformanceRating(updated.getPerformanceRating());
        existing.setAppraisalHistory(updated.getAppraisalHistory());

        existing.setSkills(updated.getSkills());
        existing.setEducation(updated.getEducation());
        existing.setProjects(updated.getProjects());
        existing.setLanguagesKnown(updated.getLanguagesKnown());

        existing.setSalary(updated.getSalary());
        existing.setBonusAmount(updated.getBonusAmount());

        existing.setLeaveBalance(updated.getLeaveBalance());

        existing.setPhotoPath(updated.getPhotoPath());
        existing.setResumePath(updated.getResumePath());

        existing.setAadhaarNumber(updated.getAadhaarNumber());
        existing.setActive(updated.getActive());

        return repo.save(existing);
    }

    /* ======================
       PARTIAL UPDATE (PATCH)
       ====================== */
    public Employee patchEmployee(UUID employeeId, Employee updated) {

        Employee existing = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (updated.getFirstName() != null)
            existing.setFirstName(updated.getFirstName());

        if (updated.getMiddleName() != null)
            existing.setMiddleName(updated.getMiddleName());

        if (updated.getLastName() != null)
            existing.setLastName(updated.getLastName());

        if (updated.getRole() != null)
            existing.setRole(updated.getRole());

        if (updated.getWorkEmail() != null)
            existing.setWorkEmail(updated.getWorkEmail());

        if (updated.getPassword() != null)
            existing.setPassword(updated.getPassword());

        if (updated.getMicrosoftId() != null)
            existing.setMicrosoftId(updated.getMicrosoftId());

        if (updated.getMobileNumber() != null)
            existing.setMobileNumber(updated.getMobileNumber());

        if (updated.getAlternatePhoneNumber() != null)
            existing.setAlternatePhoneNumber(updated.getAlternatePhoneNumber());

        if (updated.getGender() != null)
            existing.setGender(updated.getGender());

        if (updated.getNationality() != null)
            existing.setNationality(updated.getNationality());

        if (updated.getMaritalStatus() != null)
            existing.setMaritalStatus(updated.getMaritalStatus());

        if (updated.getBloodGroup() != null)
            existing.setBloodGroup(updated.getBloodGroup());

        if (updated.getCurrentAddress() != null)
            existing.setCurrentAddress(updated.getCurrentAddress());

        if (updated.getPermanentAddress() != null)
            existing.setPermanentAddress(updated.getPermanentAddress());

        if (updated.getJobTitle() != null)
            existing.setJobTitle(updated.getJobTitle());

        if (updated.getDepartment() != null)
            existing.setDepartment(updated.getDepartment());

        if (updated.getWorkMode() != null)
            existing.setWorkMode(updated.getWorkMode());

        if (updated.getEmploymentType() != null)
            existing.setEmploymentType(updated.getEmploymentType());

        if (updated.getEmployeeStatus() != null)
            existing.setEmployeeStatus(updated.getEmployeeStatus());

        if (updated.getOfficeLocation() != null)
            existing.setOfficeLocation(updated.getOfficeLocation());

        if (updated.getManagerId() != null)
            existing.setManagerId(updated.getManagerId());

        if (updated.getLeaveBalance() != null)
            existing.setLeaveBalance(updated.getLeaveBalance());

        return repo.save(existing);
    }

    /* ======================
       SOFT DELETE
       ====================== */
    public void deleteById(UUID employeeId) {
        Employee emp = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setDeleted(true);
        repo.save(emp);
    }
}
