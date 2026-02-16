package com.employee.management.mapper;

import com.employee.management.dto.*;
import com.employee.management.entity.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    // DTO → Entity
    public static Employee toEntity(EmployeeRequestDTO dto) {

        Employee emp = new Employee();

        emp.setFirstName(dto.getFirstName());
        emp.setMiddleName(dto.getMiddleName());
        emp.setLastName(dto.getLastName());
        emp.setRole(dto.getRole());

        emp.setWorkEmail(dto.getWorkEmail());
        emp.setPassword(dto.getPassword());
        emp.setMicrosoftId(dto.getMicrosoftId());

        emp.setMobileNumber(dto.getMobileNumber());
        emp.setAlternatePhoneNumber(dto.getAlternatePhoneNumber());

        emp.setDateOfBirth(dto.getDateOfBirth());
        emp.setGender(dto.getGender());
        emp.setNationality(dto.getNationality());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setBloodGroup(dto.getBloodGroup());

        emp.setCurrentAddress(dto.getCurrentAddress());
        emp.setPermanentAddress(dto.getPermanentAddress());

        emp.setEmergencyContactName(dto.getEmergencyContactName());
        emp.setEmergencyContactNumber(dto.getEmergencyContactNumber());

        emp.setJobTitle(dto.getJobTitle());
        emp.setDepartment(dto.getDepartment());
        emp.setWorkMode(dto.getWorkMode());
        emp.setEmploymentType(dto.getEmploymentType());
        emp.setEmployeeStatus(dto.getEmployeeStatus());
        emp.setDateOfJoining(dto.getDateOfJoining());
        emp.setOfficeLocation(dto.getOfficeLocation());

        emp.setManagerId(dto.getManagerId());

        emp.setShiftTiming(dto.getShiftTiming());
        emp.setPerformanceRating(dto.getPerformanceRating());
        emp.setAppraisalHistory(dto.getAppraisalHistory());

        //  Convert List → String
        if (dto.getSkills() != null) {
            emp.setSkills(String.join(",", dto.getSkills()));
        }

        emp.setEducation(dto.getEducation());
        emp.setProjects(dto.getProjects());
        emp.setLanguagesKnown(dto.getLanguagesKnown());

        emp.setSalary(dto.getSalary());
        emp.setLeaveBalance(dto.getLeaveBalance());
        emp.setAadhaarNumber(dto.getAadhaarNumber());
        emp.setActive(dto.getActive());

        return emp;
    }

    // Entity → DTO
    public static EmployeeResponseDTO toDTO(Employee emp) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setEmployeeId(emp.getEmployeeId());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setWorkEmail(emp.getWorkEmail());
        dto.setMobileNumber(emp.getMobileNumber());

        dto.setJobTitle(emp.getJobTitle());
        dto.setDepartment(emp.getDepartment());

        dto.setPerformanceRating(emp.getPerformanceRating());
        dto.setLeaveBalance(emp.getLeaveBalance());
        dto.setSalary(emp.getSalary());
        dto.setActive(emp.getActive());

        //  Convert String → List
        if (emp.getSkills() != null) {
            List<String> skillsList =
                    Arrays.stream(emp.getSkills().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());

            dto.setSkills(skillsList);
        }

        return dto;
    }
}
