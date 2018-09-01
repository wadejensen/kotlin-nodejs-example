package com.wadejensen.example

// The `actual` keyword declares this is the implementation `expect`ed by the common platform module
// which satisfies the interface IConsole for the js platform target
// (see `com.wadejensen.example.Console` in `common`).
// https://kotlinlang.org/docs/reference/multiplatform.html#platform-specific-declarations
/**
 * Console that logs in JS console object.
 */
actual class Console : IConsole {


    actual override fun println(s: String) {
        //prints text by adding it to the 'console' element
        console.log(s)
    }
}