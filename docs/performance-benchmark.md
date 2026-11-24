# API Performance Benchmark

## Objective

Evaluate the performance impact of implementing pagination in the Job Application Tracker REST API.

## Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- REST Assured
- JUnit 5
- Maven
- Flyway

## Benchmark Methodology

- Benchmark date: November 24, 2026
- Database: jobtracker_benchmark_db
- Dataset: 10,000 synthetic job applications
- Endpoint: GET /api/applications
- Environment: Local benchmark profile
- Warm-up requests per run: 10
- Measured requests per run: 100
- Number of official runs: 3
- Total measured requests: 300
- Execution model: Sequential HTTP requests
- Validation: HTTP 200 and initial dataset size verification
- Percentile calculation: Nearest-rank method

## Baseline Results — Without Pagination

| Metric | Run 1 | Run 2 | Run 3 |
|---|---:|---:|---:|
| Average latency (ms) | 91.87 | 104.01 | 96.85 |
| Minimum latency (ms) | 71 | 63 | 62 |
| Maximum latency (ms) | 149 | 156 | 162 |
| P50 (ms) | 88 | 101 | 94 |
| P95 (ms) | 113 | 135 | 125 |
| P99 (ms) | 132 | 145 | 146 |
| Payload (bytes) | 955289 | 955289 | 955289 |

## Combined Baseline Results

| Metric | Value |
|---|---:|
| Average latency | 97.58 ms |
| Minimum latency | 62 ms |
| Maximum latency | 162 ms |
| P50 latency | 94 ms |
| P95 latency | 125 ms |
| P99 latency | 146 ms |
| Average response payload | 955289 bytes |
| Successful measured requests | 300/300 |
| Observed success rate | 100% |

## Benchmark Execution

Start the application using the benchmark profile:

`mvn spring-boot:run "-Dspring-boot.run.profiles=benchmark"`

Execute the benchmark from another terminal:

`mvn "-Dtest=ApiPerformanceBenchmark" test`

## Limitations

- Measurements represent a local development environment.
- Requests are sequential rather than concurrent.
- Latency includes client-side and local network overhead.
- Payload measurements represent response body bytes.
- Results do not establish production throughput or scalability.
- Hardware specifications are pending documentation.

## Planned Optimization

Implement Spring Data JPA pagination with a page size of 20 records.

Repeat the benchmark under comparable conditions and measure the reduction in response latency and payload size.

## Post-Pagination Results

Pending implementation and measurement.