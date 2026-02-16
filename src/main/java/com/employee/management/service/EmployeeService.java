package com.employee.management.service;

import com.employee.management.dto.EmployeeRequestDTO;
import com.employee.management.dto.EmployeeResponseDTO;
import com.employee.management.entity.Employee;
import com.employee.management.repository.EmployeeRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepo repo;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    /* ====================== GET ALL ====================== */
    public List<EmployeeResponseDTO> getAll() {
        return repo.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ====================== GET BY ID ====================== */
    public EmployeeResponseDTO getById(UUID employeeId) {
        Employee emp = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToResponse(emp);
    }

    /* ====================== CREATE ====================== */
    public EmployeeResponseDTO create(EmployeeRequestDTO request) {

        Employee employee = new Employee();

        applyAllFields(employee, request, true);

        return mapToResponse(repo.save(employee));
    }

    /* ====================== PUT (FULL UPDATE) ====================== */
    public EmployeeResponseDTO updateEmployee(UUID employeeId, EmployeeRequestDTO request) {

        Employee existing = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        applyAllFields(existing, request, true);

        return mapToResponse(repo.save(existing));
    }

    /* ====================== PATCH (PARTIAL UPDATE) ====================== */
    public EmployeeResponseDTO patchEmployee(UUID employeeId, EmployeeRequestDTO request) {

        Employee existing = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        applyPatch(existing, request);

        return mapToResponse(repo.save(existing));
    }

    /* ====================== DELETE ====================== */
    public void deleteById(UUID employeeId) {
        Employee emp = repo.findByEmployeeIdAndDeletedFalse(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setDeleted(true);
        repo.save(emp);
    }

    /* =========================================================
       FIELD APPLY METHODS
       ========================================================= */

    private void applyAllFields(Employee e, EmployeeRequestDTO r, boolean encodePassword) {

        e.setFirstName(r.getFirstName());
        e.setMiddleName(r.getMiddleName());
        e.setLastName(r.getLastName());
        e.setRole(r.getRole());

        e.setWorkEmail(r.getWorkEmail());

        if (r.getPassword() != null && encodePassword) {
            e.setPassword(passwordEncoder.encode(r.getPassword()));
        }

        e.setMicrosoftId(r.getMicrosoftId());

        e.setMobileNumber(r.getMobileNumber());
        e.setAlternatePhoneNumber(r.getAlternatePhoneNumber());

        e.setDateOfBirth(r.getDateOfBirth());
        e.setGender(r.getGender());
        e.setNationality(r.getNationality());
        e.setMaritalStatus(r.getMaritalStatus());
        e.setBloodGroup(r.getBloodGroup());

        e.setCurrentAddress(r.getCurrentAddress());
        e.setPermanentAddress(r.getPermanentAddress());

        e.setEmergencyContactName(r.getEmergencyContactName());
        e.setEmergencyContactNumber(r.getEmergencyContactNumber());

        e.setJobTitle(r.getJobTitle());
        e.setDepartment(r.getDepartment());
        e.setWorkMode(r.getWorkMode());
        e.setEmploymentType(r.getEmploymentType());
        e.setEmployeeStatus(r.getEmployeeStatus());
        e.setDateOfJoining(r.getDateOfJoining());
        e.setOfficeLocation(r.getOfficeLocation());
        e.setManagerId(r.getManagerId());

        e.setShiftTiming(r.getShiftTiming());
        e.setPerformanceRating(r.getPerformanceRating());
        e.setAppraisalHistory(r.getAppraisalHistory());

        // ARRAY → STRING
        if (r.getSkills() != null) {
            e.setSkills(String.join(",", r.getSkills()));
        }

        e.setEducation(r.getEducation());
        e.setProjects(r.getProjects());
        e.setLanguagesKnown(r.getLanguagesKnown());

        e.setSalary(r.getSalary());
        e.setLeaveBalance(r.getLeaveBalance());
        e.setAadhaarNumber(r.getAadhaarNumber());
        e.setActive(r.getActive());
    }

    private void applyPatch(Employee e, EmployeeRequestDTO r) {

        if (r.getFirstName() != null) e.setFirstName(r.getFirstName());
        if (r.getMiddleName() != null) e.setMiddleName(r.getMiddleName());
        if (r.getLastName() != null) e.setLastName(r.getLastName());
        if (r.getRole() != null) e.setRole(r.getRole());
        if (r.getWorkEmail() != null) e.setWorkEmail(r.getWorkEmail());
        if (r.getPassword() != null)
            e.setPassword(passwordEncoder.encode(r.getPassword()));

        if (r.getMobileNumber() != null) e.setMobileNumber(r.getMobileNumber());
        if (r.getDepartment() != null) e.setDepartment(r.getDepartment());
        if (r.getJobTitle() != null) e.setJobTitle(r.getJobTitle());
        if (r.getEmploymentType() != null) e.setEmploymentType(r.getEmploymentType());
        if (r.getWorkMode() != null) e.setWorkMode(r.getWorkMode());
        if (r.getPerformanceRating() != null) e.setPerformanceRating(r.getPerformanceRating());
        if (r.getSalary() != null) e.setSalary(r.getSalary());
        if (r.getLeaveBalance() != null) e.setLeaveBalance(r.getLeaveBalance());
        if (r.getActive() != null) e.setActive(r.getActive());

        if (r.getSkills() != null)
            e.setSkills(String.join(",", r.getSkills()));
    }

    private EmployeeResponseDTO mapToResponse(Employee emp) {

        EmployeeResponseDTO res = new EmployeeResponseDTO();

        res.setEmployeeId(emp.getEmployeeId());
        res.setFirstName(emp.getFirstName());
        res.setMiddleName(emp.getMiddleName());
        res.setLastName(emp.getLastName());
        res.setRole(emp.getRole());
        res.setWorkEmail(emp.getWorkEmail());
        res.setMobileNumber(emp.getMobileNumber());

        res.setJobTitle(emp.getJobTitle());
        res.setDepartment(emp.getDepartment());
        res.setWorkMode(emp.getWorkMode());
        res.setEmploymentType(emp.getEmploymentType());
        res.setEmployeeStatus(emp.getEmployeeStatus());
        res.setDateOfJoining(emp.getDateOfJoining());
        res.setManagerId(emp.getManagerId());

        res.setPerformanceRating(emp.getPerformanceRating());
        res.setSalary(emp.getSalary());
        res.setLeaveBalance(emp.getLeaveBalance());
        res.setPhotoPath(emp.getPhotoPath());
        res.setResumePath(emp.getResumePath());
        res.setActive(emp.getActive());

        if (emp.getSkills() != null && !emp.getSkills().isBlank()) {
            res.setSkills(List.of(emp.getSkills().split(",")));
        }

        return res;
    }
}
