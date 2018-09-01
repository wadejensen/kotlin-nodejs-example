package pappel.http

import org.w3c.fetch.*
import pappel.JSONUtils
import kotlin.js.json

/**
 * A fake constructor for a dynamic JS RequestInit object.
 * Primarily used in calls to [[org.w3c.fetch]]
 */
fun RequestInit(
        method: Method = Method.GET,
        headers: Map<String, String?>? = null,
        body: Any? = null,
        referrer: String? = null,
        referrerPolicy: dynamic = null,
        mode: RequestMode? = null,
        credentials: RequestCredentials? = null,
        cache: RequestCache? = null,
        redirect: RequestRedirect? = null,
        integrity: String? = null,
        keepalive: Boolean? = null,
        window: Any? = null): RequestInit {

    val o = js("({})")

    o["method"] = method.value
    o["headers"] = headers
        ?.entries
        ?.map { it.toPair() }
        ?.toTypedArray()
        ?.let { json(*it) }
    o["body"] = JSON.stringify(body)
    o["referrer"] = referrer
    o["referrerPolicy"] = referrerPolicy
    o["mode"] = mode
    o["credentials"] = credentials
    o["cache"] = cache
    o["redirect"] = redirect
    o["integrity"] = integrity
    o["keepalive"] = keepalive
    o["window"] = window

    return o
}
