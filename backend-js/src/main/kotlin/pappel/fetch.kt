package pappel

import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

fun fetch(input: dynamic): Promise<Response> {
    return Fetch.jsFetch(input) as Promise<Response>
}

fun fetch(input: dynamic, init: RequestInit): Promise<Response> {
    return Fetch.jsFetch(input, init.asDynamic())
}

object Fetch {
    val jsFetch: dynamic = require("node-fetch")
}
