package com.jobtracker.service;

import com.jobtracker.model.JobApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {
    JobApplication job1 = new JobApplication(
            1L, "Infosys", "Java Developer", "APPLIED"
    );

    JobApplication job2 = new JobApplication(
            2L, "TCS", "Backend Developer", "INTERVIEW"
    );

    List<JobApplication> jobList = List.of(job1, job2);
    public JobApplication findById(Long id) {
        for (JobApplication job : jobList) {
            if (job.getId().equals(id)) {
                return job;
            }
        }
        return null;
    }

}
