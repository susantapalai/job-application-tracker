package com.jobtracker.controller;
import com.jobtracker.model.JobApplication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class JobApplicationController {

    @GetMapping("/api/applications/{id}")
    public JobApplication getApplication(@PathVariable Long id){

        JobApplication job1 = new JobApplication(
                1L, "Infosys", "Java Developer", "APPLIED"
        );

        JobApplication job2 = new JobApplication(
                2L, "TCS", "Backend Developer", "INTERVIEW"
        );

        List<JobApplication> jobList = List.of(job1, job2);

        for (JobApplication job : jobList) {
            if (job.getId().equals(id)) {
                return job;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Application not found"
        );

    }
}
