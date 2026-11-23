package com.jobtracker.service;

import com.jobtracker.model.JobApplication;
import org.springframework.stereotype.Service;
import com.jobtracker.repository.JobApplicationRepository;
import java.util.List;

@Service
public class JobApplicationService {
    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }
    public JobApplication findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public JobApplication saveApplication(JobApplication job){
        return repository.save(job);
    }
    public List<JobApplication> getAllApplications() {
        return repository.findAll();
    }
    public JobApplication updateApplication(Long id, JobApplication updatedJob) {
        JobApplication existingJob = repository.findById(id).orElse(null);
        if(existingJob == null) {
            return null;
        }
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setJobRole(updatedJob.getJobRole());
        existingJob.setStatus(updatedJob.getStatus());

        return repository.save(existingJob);
    }
    public boolean deleteApplication(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);

        return true;
    }

}
