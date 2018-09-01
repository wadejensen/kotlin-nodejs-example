package com.wadejensen.example

import kotlin.browser.document

// The `actual` keyword declares this is the implementation `expect`ed by the common platform module
// which satisfies the interface IConsole for the js platform target
// (see `com.wadejensen.example.Console` in `common`).
/**
 * Print to text emlements in the DOM, rather than the JS console.
 * (Kotlin's println is multi-platform, alternative printing is just to provide a motivation for common modules)
 */
class DOMConsole : IConsole {
    override fun println(s: String) {
        //prints text by adding it to the 'console' element
        document.getElementById("console")?.appendChild(document.createTextNode("$s\n"))
    }
}