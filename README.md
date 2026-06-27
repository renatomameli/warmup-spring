# Warmup Library for Spring Boot

Improve your first-request latency in Spring Boot applications by warming up expensive code paths at startup.
This is achieved by a simple dummy HTTP request sent to the application itself.

## Usage

Other warmup operations can be implemented by implementing the `WarmupOperation` interface and register it as bean.
These beans are automatically collected and its implementations will also be executed on startup.


## Benchmarks

JMH benchmark measuring the first HTTP request response time after application startup.

| Scenario | Mean | Forks |
|---|---|---|
| Without warmup | 103.179 ± 58.328 ms | 83, 101, 101, 105, 126 ms |
| With warmup | 2.949 ± 1.302 ms | 2.5, 2.9, 2.9, 3.0, 3.4 ms |

The warmup dummy request absorbs DispatcherServlet initialization, class loading, and JIT compilation during startup, making the first user request **~35x faster**.

### Running the Benchmarks

```bash
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 ./gradlew runJmh
```

