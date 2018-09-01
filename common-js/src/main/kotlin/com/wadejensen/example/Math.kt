package com.wadejensen.example

// The `actual` keyword declares this is the implementation `expect`ed by the common platform module
// which satisfies the interface IConsole for the js platform target
// (see `com.wadejensen.example.Console` in `common`).
// https://kotlinlang.org/docs/reference/multiplatform.html#platform-specific-declarations
actual class Math : IMath {
    // Dynamically `eval`uate JS code within Kotlin to get the JS Math singleton object
    private val mathJs: dynamic = js("Math")
    // Call to JS on dynamic object
    actual override fun sqrt(x: Double): Double = mathJs.sqrt(x)
}