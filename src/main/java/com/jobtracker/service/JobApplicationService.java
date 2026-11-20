package com.jobtracker.service;

import com.jobtracker.model.JobApplication;
import org.springframework.stereotype.Service;
import com.jobtracker.repository.JobApplicationRepository;

@Service
public class JobApplicationService {
    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplication findById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
