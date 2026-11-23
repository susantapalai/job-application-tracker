package com.jobtracker.controller;
import com.jobtracker.model.JobApplication;
import com.jobtracker.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.List;

@RestController
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping("/api/applications/{id}")
    public JobApplication getApplication(@PathVariable Long id) {
        JobApplication job = jobApplicationService.findById(id);

        if (job != null) {
            return job;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Application not found"
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/applications")
    public JobApplication createApplication(@Valid @RequestBody JobApplication job) {
        return jobApplicationService.saveApplication(job);
    }
    @GetMapping("/api/applications")
    public List<JobApplication> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }
    @PutMapping("/api/applications/{id}")
    public JobApplication updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplication updatedJob) {

        JobApplication result =
                jobApplicationService.updateApplication(id, updatedJob);

        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Application not found"
            );
        }

        return result;
    }
    @DeleteMapping("/api/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {

        boolean deleted = jobApplicationService.deleteApplication(id);

        if (!deleted) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Application not found"
            );
        }

        return ResponseEntity.noContent().build();
    }
}
