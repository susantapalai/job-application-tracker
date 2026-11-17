package com.jobtracker;

import com.jobtracker.model.JobApplication;

public class    JobApplicationDemo {

    public static void main(String[] args) {

        JobApplication job = new JobApplication(
                1L,
                "Infosys",
                "Java Developer",
                "APPLIED"
        );

        System.out.println(job.getCompanyName());

        job.setCompanyName("TCS");

        System.out.println(job.getCompanyName());
    }
}