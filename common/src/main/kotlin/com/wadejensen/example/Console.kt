package com.wadejensen.example

// The `expect` keyword declares that the common platform module expects that at compile time their exists a
// Console class which satisfies the interface IConsole for the specific target platform
// (see `com.wadejensen.example.Console` in `common_js`).
// https://kotlinlang.org/docs/reference/multiplatform.html#platform-specific-declarations
/**
 * Console shared code declaration. Class is used to output something on screen.
 */
expect class Console : IConsole {

    override fun println(s: String)

}