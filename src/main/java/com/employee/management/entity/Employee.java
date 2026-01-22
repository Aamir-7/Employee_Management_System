package com.employee.management.entity;

import com.employee.management.enums.EmployeeStatus;
import com.employee.management.enums.EmploymentType;
import com.employee.management.enums.Role;
import com.employee.management.enums.WorkMode;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
public class Employee {

    /* ======================
       PRIMARY KEY (UUID)
       ====================== */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID employeeId;

    /* ======================
       NAME
       ====================== */
    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    /* ======================
       AUTH / SYSTEM
       ====================== */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    private String workEmail;

    private String password;
    private String microsoftId;

    /* ======================
       CONTACT
       ====================== */
    @Column(nullable = false)
    private String mobileNumber;

    private String alternatePhoneNumber;

    /* ======================
       PERSONAL INFO
       ====================== */
    private LocalDate dateOfBirth;

    private String gender;
    private String nationality;
    private String maritalStatus;
    private String bloodGroup;

    @Column(columnDefinition = "TEXT")
    private String currentAddress;

    @Column(columnDefinition = "TEXT")
    private String permanentAddress;

    /* ======================
       EMERGENCY
       ====================== */
    private String emergencyContactName;
    private String emergencyContactNumber;

    /* ======================
       JOB DETAILS
       ====================== */
    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus employeeStatus = EmployeeStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDate dateOfJoining;

    private String officeLocation;

    /* ======================
       REPORTING
       ====================== */
    private UUID managerId;

    /* ======================
       PERFORMANCE
       ====================== */
    private String shiftTiming;
    private Double performanceRating;

    @Column(columnDefinition = "TEXT")
    private String appraisalHistory;

    /* ======================
       SKILLS
       ====================== */
    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(columnDefinition = "TEXT")
    private String projects;

    @Column(columnDefinition = "TEXT")
    private String languagesKnown;

    /* ======================
       FINANCIAL
       ====================== */
    private BigDecimal salary;
    private Double bonusAmount;

    /* ======================
       LEAVE
       ====================== */
    @Column(nullable = false)
    private Integer leaveBalance = 20;

    /* ======================
       DOCUMENTS
       ====================== */
    private String photoPath;
    private String resumePath;

    /* ======================
       LEGAL
       ====================== */
    @Column(nullable = false, unique = true, length = 12)
    private String aadhaarNumber;

    /* ======================
       SYSTEM FLAGS
       ====================== */
    @Column(nullable = false)
    private Boolean active = true;

    private Boolean deleted = false;

    /* ======================
       CONSTRUCTORS
       ====================== */
    public Employee() {
    }

    public Employee(
            String firstName,
            String middleName,
            String lastName,
            Role role,
            String workEmail,
            String password,
            String microsoftId,
            String mobileNumber,
            String alternatePhoneNumber,
            LocalDate dateOfBirth,
            String gender,
            String nationality,
            String maritalStatus,
            String bloodGroup,
            String currentAddress,
            String permanentAddress,
            String emergencyContactName,
            String emergencyContactNumber,
            String jobTitle,
            String department,
            WorkMode workMode,
            EmploymentType employmentType,
            LocalDate dateOfJoining,
            String officeLocation,
            UUID managerId,
            String shiftTiming,
            Double performanceRating,
            String appraisalHistory,
            String skills,
            String education,
            String projects,
            String languagesKnown,
            BigDecimal salary,
            Double bonusAmount,
            Integer leaveBalance,
            String photoPath,
            String resumePath,
            String aadhaarNumber
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.role = role;
        this.workEmail = workEmail;
        this.password = password;
        this.microsoftId = microsoftId;
        this.mobileNumber = mobileNumber;
        this.alternatePhoneNumber = alternatePhoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.bloodGroup = bloodGroup;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
        this.jobTitle = jobTitle;
        this.department = department;
        this.workMode = workMode;
        this.employmentType = employmentType;
        this.dateOfJoining = dateOfJoining;
        this.officeLocation = officeLocation;
        this.managerId = managerId;
        this.shiftTiming = shiftTiming;
        this.performanceRating = performanceRating;
        this.appraisalHistory = appraisalHistory;
        this.skills = skills;
        this.education = education;
        this.projects = projects;
        this.languagesKnown = languagesKnown;
        this.salary = salary;
        this.bonusAmount = bonusAmount;
        this.leaveBalance = leaveBalance;
        this.photoPath = photoPath;
        this.resumePath = resumePath;
        this.aadhaarNumber = aadhaarNumber;
    }

    /* ======================
       GETTERS & SETTERS
       ====================== */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMicrosoftId() {
        return microsoftId;
    }

    public void setMicrosoftId(String microsoftId) {
        this.microsoftId = microsoftId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public String getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(String shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    public Double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(Double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public String getAppraisalHistory() {
        return appraisalHistory;
    }

    public void setAppraisalHistory(String appraisalHistory) {
        this.appraisalHistory = appraisalHistory;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getLanguagesKnown() {
        return languagesKnown;
    }

    public void setLanguagesKnown(String languagesKnown) {
        this.languagesKnown = languagesKnown;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Double getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Integer getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Integer leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

}
