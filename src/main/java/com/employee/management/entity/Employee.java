package com.employee.management.entity;

import com.employee.management.enums.Role;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------- AUTH / IDENTITY --------
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    // -------- BASIC INFO --------
    private String mobileNum;
    private String education;
    private String jobTitle;
    private String gender;
    private String department;
    private String workMode;

    // -------- PERSONAL / WORK DETAILS --------
    private LocalDate dateOfBirth;
    private String address;
    private BigDecimal salary;
    private String performance;
    private LocalTime inTime;
    private LocalTime outTime;
    private String workingHours;
    private String workStatus;
    private Long manager; // stores manager's employee ID
    private Integer leaveBalance = 20;
    private String task;

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    // -------- SOFT DELETE --------
    private Boolean deleted = false;

    // -------- REQUIRED BY JPA --------
    public Employee() {
    }

    // -------- FULL CONSTRUCTOR (OPTIONAL, SAFE) --------
    public Employee(
            String username,
            String email,
            String password,
            Role role,
            String mobileNum,
            String education,
            String jobTitle,
            String gender,
            String department,
            String workMode,
            LocalDate dateOfBirth,
            String address,
            BigDecimal salary,
            String performance,
            LocalTime inTime,
            LocalTime outTime,
            String workingHours,
            String workStatus,
            String task
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.mobileNum = mobileNum;
        this.education = education;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.department = department;
        this.workMode = workMode;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.salary = salary;
        this.performance = performance;
        this.inTime = inTime;
        this.outTime = outTime;
        this.workingHours = workingHours;
        this.workStatus = workStatus;
        this.task = task;
        this.deleted = false;
    }

    // -------- GETTERS & SETTERS --------

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public LocalTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalTime inTime) {
        this.inTime = inTime;
    }

    public LocalTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalTime outTime) {
        this.outTime = outTime;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Integer leaveBalance) {
        this.leaveBalance = leaveBalance;
    }
}
