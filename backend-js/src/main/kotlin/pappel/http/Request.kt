/**
 * Adapted from Raphael Stäbler's Pappel Node.js framework for Kotlin
 * https://github.com/blazer82/pappel-framework
 */

package pappel.http

import pappel.JSONUtils

class Request(external private val req: dynamic) {

    val baseURL: String
    val body: Map<String, Any>?
    val cookies: Map<String, String>?
    val hostname: String
    val ip: String
    val ips: Array<String>?
    val method: Method
    val parameters: Map<String, String>?
    val path: String
    val protocol: Protocol
    val query: Map<String, Any>?
    // TODO: Add route

    init {
        baseURL = req.baseUrl as String
        body = null
        cookies = null
        hostname = req.hostname as String
        ip = req.ip as String
        ips = req.ips as? Array<String>
        method = Method.valueOf(req.method as String)
        parameters = JSONUtils.retrieveMap(req.params) as? Map<String, String>
        path = req.path as String
        protocol = Protocol.valueOf((req.protocol as String).toUpperCase())
        query = JSONUtils.retrieveMap(req.query) as? Map<String, Any>
    }

}