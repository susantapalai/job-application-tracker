package com.jobtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message =  "Company name is required")
    private String companyName;
    @NotBlank(message =  "Job role is required")
    private String jobRole;
    @NotNull(message = "Application status is required")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    protected JobApplication() {
    }

    public JobApplication(Long id, String companyName,
                          String jobRole, ApplicationStatus status) {
        this.id = id;
        this.companyName = companyName;
        this.jobRole = jobRole;
        this.status = status;
    }
    public JobApplication(String companyName,
                          String jobRole,
                          ApplicationStatus status) {
        this.companyName = companyName;
        this.jobRole = jobRole;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
