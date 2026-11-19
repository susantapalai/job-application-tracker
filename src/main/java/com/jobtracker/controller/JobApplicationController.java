package com.jobtracker.controller;
import com.jobtracker.model.JobApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobApplicationController {

    @GetMapping("/api/applications/{id}")
    public JobApplication getApplication(@PathVariable Long id){

        return new JobApplication(
                id,
                "Infosys",
                "Java Developer",
                "APPLIED"
        );
    }
}
