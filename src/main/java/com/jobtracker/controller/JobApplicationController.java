package com.jobtracker.controller;
import com.jobtracker.model.JobApplication;
import com.jobtracker.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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
}
