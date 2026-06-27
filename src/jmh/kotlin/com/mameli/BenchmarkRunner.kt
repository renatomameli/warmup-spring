package com.mameli

import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

fun main() {
    Runner(
        OptionsBuilder()
            .include(".*WarmupBenchmark.*")
            .build()
    ).run()
}
