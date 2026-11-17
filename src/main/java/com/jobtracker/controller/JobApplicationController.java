package com.jobtracker.controller;
import com.jobtracker.model.JobApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobApplicationController {

    @GetMapping("/api/applications/1")
    public JobApplication getApplication(){

        return new JobApplication(
                1L,
                "Infosys",
                "Java Developer",
                "APPLIED"
        );
    }
}
