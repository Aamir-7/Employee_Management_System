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

        /* PERSONAL INFO */
        if (updated.getDateOfBirth() != null)
            existing.setDateOfBirth(updated.getDateOfBirth());

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

        /* EMERGENCY */
        if (updated.getEmergencyContactName() != null)
            existing.setEmergencyContactName(updated.getEmergencyContactName());

        if (updated.getEmergencyContactNumber() != null)
            existing.setEmergencyContactNumber(updated.getEmergencyContactNumber());

        /* JOB DETAILS */
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

        if (updated.getDateOfJoining() != null)
            existing.setDateOfJoining(updated.getDateOfJoining());

        if (updated.getOfficeLocation() != null)
            existing.setOfficeLocation(updated.getOfficeLocation());

        /* REPORTING */
        if (updated.getManagerId() != null)
            existing.setManagerId(updated.getManagerId());

        /* PERFORMANCE */
        if (updated.getShiftTiming() != null)
            existing.setShiftTiming(updated.getShiftTiming());

        if (updated.getPerformanceRating() != null)
            existing.setPerformanceRating(updated.getPerformanceRating());

        if (updated.getAppraisalHistory() != null)
            existing.setAppraisalHistory(updated.getAppraisalHistory());

        /* SKILLS */
        if (updated.getSkills() != null)
            existing.setSkills(updated.getSkills());

        if (updated.getEducation() != null)
            existing.setEducation(updated.getEducation());

        if (updated.getProjects() != null)
            existing.setProjects(updated.getProjects());

        if (updated.getLanguagesKnown() != null)
            existing.setLanguagesKnown(updated.getLanguagesKnown());

        /* FINANCIAL */
        if (updated.getSalary() != null)
            existing.setSalary(updated.getSalary());

        if (updated.getBonusAmount() != null)
            existing.setBonusAmount(updated.getBonusAmount());

        /* LEAVE */
        if (updated.getLeaveBalance() != null)
            existing.setLeaveBalance(updated.getLeaveBalance());

        /* DOCUMENTS */
        if (updated.getPhotoPath() != null)
            existing.setPhotoPath(updated.getPhotoPath());

        if (updated.getResumePath() != null)
            existing.setResumePath(updated.getResumePath());

        /* LEGAL */
        if (updated.getAadhaarNumber() != null)
            existing.setAadhaarNumber(updated.getAadhaarNumber());

        /* SYSTEM FLAGS */
        if (updated.getActive() != null)
            existing.setActive(updated.getActive());

        if (updated.getDeleted() != null)
            existing.setDeleted(updated.getDeleted());

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
