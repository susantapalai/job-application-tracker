package com.jobtracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobApplicationApiTest {

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnJobApplication() {

        given()
                .port(port)
                .when()
                .get("/api/applications/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("companyName", equalTo("Infosys"))
                .body("jobRole", equalTo("Java Developer"))
                .body("status", equalTo("APPLIED"));

    }
    @Test
    void shouldReturnApplicationWithDynamicId() {

        given()
                .port(port)
        .when()
                .get("/api/applications/1200")
        .then()
                .statusCode(200)
                .body("id", equalTo(1200));

    }

    @Test
    void shouldRejectInvalidApplicationId() {

        given()
                .port(port)
        .when()
                .get("/api/applications/abc")
        .then()
                .statusCode(400);

    }


}