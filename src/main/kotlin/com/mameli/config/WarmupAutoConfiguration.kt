package com.mameli.config

import com.mameli.ApplicationStartupEventListener
import com.mameli.WarmupOperation
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnClass(ApplicationStartupEventListener::class)
open class WarmupAutoConfiguration(private val warmupOperations: List<WarmupOperation> = emptyList()) {

    @Bean
    open fun applicationStartupEventListener() = ApplicationStartupEventListener(warmupOperations)
}
