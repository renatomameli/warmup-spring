package com.mameli

/**
 * A functional interface representing a single warmup operation.
 *
 * Implementations of this interface should encapsulate logic that prepares
 * the application for faster initial requests (e.g., preloading caches,
 * initializing expensive components, or triggering background tasks).
 *
 * Typical usage:
 * - Implement this interface as a `@Bean` in a Spring context.
 * - All beans of type [WarmupOperation] will automatically be collected
 *   and executed by [ApplicationStartupEventListener] once the application starts.
 *
 * Example:
 * ```
 * @Bean
 * fun cacheWarmupOperation(): WarmupOperation =
 *     WarmupOperation { myCache.preload() }
 * ```
 */
fun interface WarmupOperation {
    /**
     * Executes the warmup operation.
     */
    fun invoke()
}