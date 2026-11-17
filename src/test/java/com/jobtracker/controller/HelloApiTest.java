package com.jobtracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class HelloApiTest {

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnWelcomeMessage() {
    given()
            .port(port)
    .when()
            .get("api/hello")
    .then()
            .statusCode(200)
            .body(equalTo("Welcome to Job Application Tracker API"));
    }

}
