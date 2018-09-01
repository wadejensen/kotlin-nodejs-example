package com.wadejensen.example

// The `expect` keyword declares that the common platform module expects that at compile time their exists a Math class
// which satisfies the interface IMath for the specific target platform
// (see `com.wadejensen.example.Math` in `common_js`).
// https://kotlinlang.org/docs/reference/multiplatform.html#platform-specific-declarations
expect class Math : IMath {

    override fun sqrt(x: Double): Double

}