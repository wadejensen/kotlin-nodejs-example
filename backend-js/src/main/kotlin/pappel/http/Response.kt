/**
 * Adapted from Raphael Stäbler's Pappel Node.js framework for Kotlin
 * https://github.com/blazer82/pappel-framework
 */

package pappel.http

import pappel.JSONUtils

/**
 * HTTP Response class.
 *
 * Encapsulates HTTP responses as used within [Router] callbacks.
 * @constructor Creates a new Response based on an expressjs response.
 */
class Response(external private val res: dynamic) {

    /**
     * Ends response.
     * May be called instead of [send] or [sendJSON].
     */
    fun end() {
        res.end()
    }

    /**
     * Renders [view] and sends it to the client.
     * @param view Relative path to view
     */
    fun render(view: String) {
        res.render("$view.html")
    }

    /**
     * Renders [view] and sends it to [callback].
     * @param view Relative path to view
     * @param callback Callback to process rendered view in
     */
    fun render(view: String, callback: (String?) -> Unit) {
        res.render("$view.html") {
            _, html -> callback.invoke(html as? String)
        }
    }

    /**
     * Renders [view] using [parameters] and sends it to the client.
     * @param view Relative path to view
     * @param parameters Parameters to pass through to view template
     */
    fun render(view: String, parameters: Map<String, Any>) {
        res.render("$view.html", JSONUtils.toJSON(parameters))
    }

    /**
     * Renders [view] using [parameters] and sends it to [callback].
     * @param view Relative path to view
     * @param parameters Parameters to pass through to view template
     * @param callback Callback to process rendered view in
     */
    fun render(view: String, parameters: Map<String, Any>, callback: (String?) -> Unit) {
        res.render("$view.html", JSONUtils.toJSON(parameters)) {
            _, html -> callback.invoke(html as? String)
        }
    }

    /**
     * Sends response [string].
     * @param string String to send to the client
     */
    fun send(string: String) {
        res.send(string)
    }

    /**
     * Sends [data] response as JSON.
     * @param data Map to send to the client as JSON
     */
    fun sendJSON(data: Map<String, Any?>) {
        res.json(JSONUtils.toJSON(data))
    }

    /**
     * Sends [data] response as JSON.
     * @param data Iterable to send to the client as JSON
     */
    fun sendJSON(data: Iterable<Any?>) {
        res.json(JSONUtils.toJSON(data))
    }

    /**
     * Sets content [type] HTTP header.
     * @param type Content type string
     */
    fun setContentType(type: String) {
        res.type(type)
    }

    /**
     * Sets HTTP header [field] to [value].
     * @param field Header field name
     * @param value Header field value
     */
    fun setHeader(field: String, value: String) {
        res.append(field, value)
    }

    /**
     * Sets multiple HTTP headers.
     * @param fields Header field names and values
     */
    fun setHeaders(fields: Map<String, String>) {
        fields.forEach {
            entry -> res.append(entry.key, entry.value)
        }
    }

    /**
     * Sets HTTP [status]
     * @param status [Status]
     */
    fun setStatus(status: Status) {
        res.status(status.code)
    }

}