package com.alorma.playground

/**
 * A simple greeting class that works across all platforms
 */
class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello from ${platform.name}!"
    }
}

/**
 * Platform interface to be implemented by each target
 */
expect class Platform() {
    val name: String
}

/**
 * Get the current platform
 */
fun getPlatform(): Platform = Platform()
