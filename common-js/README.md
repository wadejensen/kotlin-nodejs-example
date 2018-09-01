#### A common Kotlin module which can be shared between JS targets on both the frontend and backend.
#### Can be used a a dependency for Kotlin JS in the browser, as well as Node.js

Useful for defining JS specific code, eg. networking, file system interaction, Promises

The example module exposes a `SharedClass` which calculates and prints prime numbers.
The implementation of printing and the math library are passed as interfaces 
to show how they can be overridden in the platform-specific libs. 
See `com.wadejensen.example.console` and `com.wadejensen.example.Math` in `common-js` to see
```kt
class SharedClass(val console: IConsole, val math: IMath) {
    var platform: String = ""
    fun printMe()
    fun printPrimes(n: Long)
    fun givePrimes(n: Long): List<Long>
```

Note: It is possible to use the default `println` and `kotlin.math.sqrt` which Kotlin provides.
These pieces of functionality are just passed as parameters to motivate the example project.