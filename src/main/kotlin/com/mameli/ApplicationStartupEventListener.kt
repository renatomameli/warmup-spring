package com.mameli

import com.sun.org.slf4j.internal.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

open class ApplicationStartupEventListener(private val warmupOperations: Collection<WarmupOperation>) :
    ApplicationListener<ApplicationStartedEvent> {

    companion object {
        const val DUMMY_HTTP_PATH: String = "/api/dummy-warmup"
        private val log = LoggerFactory.getLogger(ApplicationStartupEventListener::class.java)
    }

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        val port = event.applicationContext.environment.getProperty("local.server.port")?.toIntOrNull()?: 8080
        warmup(port)

        warmupOperations.forEach { it.invoke() }
    }

    private fun warmup(port: Int) {
        val dummyRequestUrl = "http://localhost:$port$DUMMY_HTTP_PATH"

        try {
            RestTemplate().getForObject(dummyRequestUrl, Void::class.java)
        } catch (_: HttpClientErrorException) {
            // Always fails
        }

        log.debug("Dummy request has been sent")
    }
}