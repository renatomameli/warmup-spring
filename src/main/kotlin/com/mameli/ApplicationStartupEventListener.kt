package com.mameli

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

open class ApplicationStartupEventListener(private val warmupOperations: Collection<WarmupOperation>) :
    ApplicationListener<ApplicationStartedEvent> {

    companion object {
        const val LOCALHOST_PATH: String = "http://localhost:"
        const val DUMMY_HTTP_PATH: String = "/api/dummy-warmup"
        private val log = LoggerFactory.getLogger(ApplicationStartupEventListener::class.java)
    }

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        sendDummyRequest(event.applicationContext.environment.getProperty("local.server.port")?.toIntOrNull()?: 8080)

        warmupOperations.forEach { it.invoke() }

        log.info("Warmup complete")
    }

    private fun sendDummyRequest(port: Int) {
        try {
            RestTemplate().exchange("$LOCALHOST_PATH$port$DUMMY_HTTP_PATH", HttpMethod.GET, null, String::class.java)
        } catch (_: Exception) {
            // Ignore
        }

        log.debug("Dummy request has been sent")
    }
}