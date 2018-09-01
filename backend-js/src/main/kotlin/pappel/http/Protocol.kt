/**
 * Adapted from Raphael Stäbler's Pappel Node.js framework for Kotlin
 * https://github.com/blazer82/pappel-framework
 */

package pappel.http

/**
 * Enum type for HTTP protocols.
 */
enum class Protocol(val value: String) {
    HTTP("http"),
    HTTPS("https"),
}