#### A common Kotlin module which does not contain any platform specific code.
#### Can be used a a dependency for Kotin JS, Kotlin JVM, and (in future) Kotlin Native modules

The common platform module is used for logic which is not specific to any platform.
It is useful for core business rules and data models.

The example module exposes a `SharedClass` which calculates and prints prime numbers.
The implementation of printing and the math library are passed as interfaces 
to show how they can be overridden in the platform-specific libs. 
See common_js `com.wadejensen.example.console` and `com.wadejensen.example.Math` in `common_js` to see
```kt
class SharedClass(val console: IConsole, val math: IMath) {
    var platform: String = ""
    fun printMe()
    fun printPrimes(n: Long)
    fun givePrimes(n: Long): List<Long>
```

Note: It is possible to use the default `println` and `kotlin.math.sqrt` which Kotlin provides.
These pieces of functionality are just passed as parameters to motivate the example project.