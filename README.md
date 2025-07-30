# Warmup Library for Spring Boot

Improve your first-request latency in Spring Boot applications by warming up expensive code paths at startup.
This is achieved by a simple dummy HTTP request sent to the application itself.

## Usage

Other warmup operations can be implemented by implementing the `WarmupOperation` interface and register it as bean.
These beans are automatically collected and its implementations will also be exeucted on startup.


This library was tested extensively under various conditions to measure cold-start response time. Each test measured the first HTTP request latency right after application startup — the worst-case scenario for many Spring Boot applications.

## Average Results (over many test runs):
- Without warmup: ~53 ms
- With warmup: ~8 ms

That’s an average improvement of around 6.5x in response time.

## How It Was Tested
- Warmup was tested multiple times manually using Postman.
- After starting the Spring Boot application, the first request was triggered via Postman.
- Warmup was tested with and without the library enabled.
- All values were recorded manually across several runs to ensure reliability.

