package com.jobtracker.benchmark;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

class ApiPerformanceBenchmark{

    @Test
    void benchmarkGetApplications() {

        String url =
                "http://localhost:8080/api/applications";

        System.out.println("Starting warm-up...");

        given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .body("size()", equalTo(10000));

        System.out.println("Dataset verified: 10000 records");

        for (int i = 0; i < 10; i++) {

            given()
                    .when()
                    .get(url)
                    .then()
                    .statusCode(200);
        }

        System.out.println("Warm-up completed.");

        List<Long> responseTimes = new ArrayList<>();

        long totalPayloadSize = 0;

        System.out.println("Starting performance measurement...");

        for (int i = 0; i < 100; i++) {

            Response response = given()
                    .when()
                    .get(url);

            response.then().statusCode(200);

            long responseTime = response.time();

            responseTimes.add(responseTime);

            long payloadSize = response.asByteArray().length;
            totalPayloadSize += payloadSize;
        }

        System.out.println("Total measurements: "
                + responseTimes.size());

        System.out.println("Response times: "
                + responseTimes);

        long totalTime = 0;

        for (long time : responseTimes) {
            totalTime = totalTime + time;
        }

        double averageTime =
                (double) totalTime / responseTimes.size();

        System.out.println("Average response time: "
                + averageTime + " ms");

        long minTime = Collections.min(responseTimes);

        long maxTime = Collections.max(responseTimes);

        System.out.println("Minimum response time: "
                + minTime + " ms");

        System.out.println("Maximum response time: "
                + maxTime + " ms");

        Collections.sort(responseTimes);

        int p50Index = (int) Math.ceil(
                0.50 * responseTimes.size()) - 1;

        int p95Index = (int) Math.ceil(
                0.95 * responseTimes.size()) - 1;

        int p99Index = (int) Math.ceil(
                0.99 * responseTimes.size()) - 1;

        long p50 = responseTimes.get(p50Index);
        long p95 = responseTimes.get(p95Index);
        long p99 = responseTimes.get(p99Index);

        System.out.println("P50 latency: " + p50 + " ms");
        System.out.println("P95 latency: " + p95 + " ms");
        System.out.println("P99 latency: " + p99 + " ms");

        double averagePayloadSize =
                (double) totalPayloadSize / responseTimes.size();
        System.out.println("Average payload size: "
                + averagePayloadSize + " bytes");
        System.out.println("Average payload size in KB: "
                + averagePayloadSize / 1024 + " KiB");
    }
}