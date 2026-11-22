package com.jobtracker.controller;

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
                "APPLIED"
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
                "APPLIED"
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
}