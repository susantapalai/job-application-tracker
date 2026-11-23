package com.jobtracker.controller;

import com.jobtracker.model.ApplicationStatus;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.jobtracker.model.JobApplication;
import com.jobtracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobApplicationApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JobApplicationRepository repository;

    @Test
    void shouldRejectInvalidApplicationId() {

        given()
                .port(port)
        .when()
                .get("/api/applications/abc")
        .then()
                .statusCode(400);

    }

    @Test
    void shouldReturn404WhenApplicationDoesNotExist() {

        JobApplication job = new JobApplication(
                "Missing Company",
                "Java Developer",
                ApplicationStatus.APPLIED
        );

        JobApplication savedJob = repository.save(job);

        Long deletedId = savedJob.getId();

        repository.deleteById(deletedId);

        given()
                .port(port)
                .when()
                .get("/api/applications/" + deletedId)
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnSavedJobApplication() {

        JobApplication job = new JobApplication(
                "Accenture",
                "Backend Developer",
                ApplicationStatus.APPLIED
        );

        JobApplication savedJob = repository.save(job);

        try {
            given()
                    .port(port)
                    .when()
                    .get("/api/applications/" + savedJob.getId())
                    .then()
                    .statusCode(200)
                    .body("companyName", equalTo("Accenture"))
                    .body("jobRole", equalTo("Backend Developer"))
                    .body("status", equalTo("APPLIED"));

        } finally {
            repository.deleteById(savedJob.getId());
        }
    }

    @Test
    void shouldCreateJobApplication() {
        String requestBody = """
                {
                    "companyName": "TCS",
                    "jobRole": "Backend Developer",
                    "status": "APPLIED"
                }
                """;

        Response response = given()
                .port(port)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/applications");

        try {
            response.then()
                    .statusCode(201)
                    .body("companyName", equalTo("TCS"))
                    .body("jobRole", equalTo("Backend Developer"))
                    .body("status", equalTo("APPLIED"));

        } finally {
            Long createdId = response.jsonPath().getLong("id");

            if (createdId != null) {
                repository.deleteById(createdId);
            }
        }
    }
    @Test
    void shouldRejectBlankCompanyName() {

        String requestBody = """
            {
                "companyName": "",
                "jobRole": "Java Developer",
                "status": "APPLIED"
            }
            """;

        given()
                .port(port)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/applications")
                .then()
                .statusCode(400);
    }
    @Test
    void shouldRejectBlankJobRole() {

        String requestBody = """
            {
                "companyName": "Infosys",
                "jobRole": "",
                "status": "APPLIED"
            }
            """;

        given()
                .port(port)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/applications")
                .then()
                .statusCode(400);
    }
    @Test
    void shouldRejectBlankStatus() {

        String requestBody = """
            {
                "companyName": "Infosys",
                "jobRole": "Java Developer",
                "status": ""
            }
            """;

        given()
                .port(port)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/applications")
                .then()
                .statusCode(400);
    }
    @Test
    void shouldRejectInvalidApplicationStatus() {

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("""
            {
              "companyName": "Infosys",
              "jobRole": "Java Developer",
              "status": "PIZZA"
            }
            """)
                .when()
                .post("/api/applications")
                .then()
                .log().all()
                .statusCode(400);
    }
    @Test
    void shouldReturnAllJobApplications() {

        JobApplication job = new JobApplication(
                "Infosys",
                "Java Developer",
                ApplicationStatus.APPLIED
        );

        JobApplication savedJob = repository.save(job);

        try {
            given()
                    .port(port)
                    .when()
                    .get("/api/applications")
                    .then()
                    .statusCode(200)
                    .body("find { it.id == " + savedJob.getId() + " }.companyName",
                            equalTo("Infosys"));

        } finally {
            repository.deleteById(savedJob.getId());
        }
    }
    @Test
    void shouldReturnMultipleJobApplications() {

        JobApplication firstJob = new JobApplication(
                "Infosys",
                "Java Developer",
                ApplicationStatus.APPLIED
        );

        JobApplication secondJob = new JobApplication(
                "TCS",
                "Backend Developer",
                ApplicationStatus.INTERVIEW
        );

        JobApplication savedFirst = repository.save(firstJob);
        JobApplication savedSecond = null;

        try {
            savedSecond = repository.save(secondJob);

            given()
                    .port(port)
                    .when()
                    .get("/api/applications")
                    .then()
                    .statusCode(200)
                    .body("find { it.companyName == 'Infosys' }.jobRole",
                            equalTo("Java Developer"))
                    .body("find { it.companyName == 'TCS' }.status",
                            equalTo("INTERVIEW"));

        } finally {
            repository.deleteById(savedFirst.getId());

            if (savedSecond != null) {
                repository.deleteById(savedSecond.getId());
            }
        }
    }
    @Test
    void shouldUpdateExistingJobApplication() {

        JobApplication job = new JobApplication(
                "Amazon",
                "Software Engineer",
                ApplicationStatus.APPLIED
        );

        JobApplication savedJob = repository.save(job);

        try {
            String requestBody = """
                {
                    "companyName": "Amazon",
                    "jobRole": "Java Backend Developer",
                    "status": "INTERVIEW"
                }
                """;

            given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .put("/api/applications/" + savedJob.getId())
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(savedJob.getId().intValue()))
                    .body("companyName", equalTo("Amazon"))
                    .body("jobRole", equalTo("Java Backend Developer"))
                    .body("status", equalTo("INTERVIEW"));

        } finally {
            repository.deleteById(savedJob.getId());
        }
    }
    @Test
    void shouldReturn404WhenUpdatingMissingApplication() {

        JobApplication job = new JobApplication(
                "Missing Company",
                "Java Developer",
                ApplicationStatus.APPLIED
        );

        JobApplication savedJob = repository.save(job);

        Long deletedId = savedJob.getId();

        repository.deleteById(deletedId);

        String requestBody = """
            {
                "companyName": "Amazon",
                "jobRole": "Java Backend Developer",
                "status": "INTERVIEW"
            }
            """;

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/api/applications/" + deletedId)
                .then()
                .statusCode(404);
    }
    @Test
    void shouldRejectBlankCompanyNameWhenUpdating() {

        JobApplication job = new JobApplication(
                "Amazon",
                "Software Engineer",
                ApplicationStatus.APPLIED
        );

        JobApplication savedJob = repository.save(job);

        try {
            String requestBody = """
                {
                    "companyName": "",
                    "jobRole": "Java Backend Developer",
                    "status": "INTERVIEW"
                }
                """;

            given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .put("/api/applications/" + savedJob.getId())
                    .then()
                    .statusCode(400);

            given()
                    .port(port)
                    .when()
                    .get("/api/applications/" + savedJob.getId())
                    .then()
                    .statusCode(200)
                    .body("companyName", equalTo("Amazon"))
                    .body("jobRole", equalTo("Software Engineer"))
                    .body("status", equalTo("APPLIED"));

        } finally {
            repository.deleteById(savedJob.getId());
        }
    }
}