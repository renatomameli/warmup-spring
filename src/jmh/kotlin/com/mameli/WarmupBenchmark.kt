package com.mameli

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.openjdk.jmh.annotations.Warmup
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@SpringBootApplication
open class TestApplication

@RestController
open class DummyController {

    @GetMapping("/api/dummy-warmup")
    fun handle(): String = "ok"
}

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 0)
@Measurement(iterations = 1)
@Fork(5)
open class WarmupBenchmark {

    @Param("true", "false")
    var warmupEnabled: String = "true"

    private lateinit var context: ConfigurableApplicationContext
    private var port: Int = 0

    @Setup(Level.Trial)
    fun setup() {
        System.setProperty("warmup.enabled", warmupEnabled)
        context = SpringApplication(TestApplication::class.java).run()
        port = context.environment.getProperty("local.server.port", Int::class.java, 8080)
    }

    @Benchmark
    fun firstRequest() {
        RestTemplate().getForObject("http://localhost:$port/api/dummy-warmup", String::class.java)
    }

    @TearDown(Level.Trial)
    fun tearDown() {
        context.close()
        System.clearProperty("warmup.enabled")
    }
}
