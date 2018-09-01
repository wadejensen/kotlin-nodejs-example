/**
 * Adapted from Raphael Stäbler's Pappel Node.js framework for Kotlin
 * https://github.com/blazer82/pappel-framework
 */

package pappel

import kotlin.js.Json
import kotlin.js.json

/**
 * JSON utilities for easier conversion of types between JavaScript and Kotlin.
 *
 * Most of the time these functions will be directly incorporated into the framework
 * methods and won't be used in any application code.
 */
class JSONUtils {

    companion object {

        /**
         * Converts a map of [data] into JSON.
         * @return Json
         */
        fun toJSON(data: Map<String, Any?>): Json {
            val arrayOfPairs: Array<Pair<String, Any?>> = data.map {
                entry ->
                when {
                    entry.value is Map<*, *> -> {
                        Pair(entry.key, toJSON(entry.value as Map<String, Any?>))
                    }
                    entry.value is Iterable<*> -> {
                        Pair(entry.key, toJSON(entry.value as Iterable<Any?>))
                    }
                    else -> {
                        entry.toPair()
                    }
                }
            }.toTypedArray()

            return json(*arrayOfPairs)
        }

        /**
         * Converts any iterable [data] into JSON.
         * @return Json
         */
        fun toJSON(data: Iterable<Any?>): Array<Any?> {
            val array: Array<Any?> = data.map {
                entry ->
                if (entry is Map<*,*>) {
                    toJSON(entry as Map<String, Any?>)
                }
                else {
                    entry
                }
            }.toTypedArray()

            return array
        }

        /**
         * Converts any JSON input data to a map.
         * @return Map<String, Any?>
         */
        fun retrieveMap(json: Any): Map<String, Any?>? {
            if (jsTypeOf(json) != "object") {
                return null
            }

            val map: MutableMap<String, Any?> = mutableMapOf()

            val keys = js("Object.keys(json)") as Array<String>

            for (key in keys) {
                when {
                    js("typeof json[key]") == "object" -> {
                        map.put(key, retrieveMap(js("json[key]") as Any))
                    }
                    else -> {
                        map.put(key, js("json[key]") as String)
                    }
                }
            }

            return map
        }
    }

}
