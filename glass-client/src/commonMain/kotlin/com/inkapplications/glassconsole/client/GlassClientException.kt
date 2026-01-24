package com.inkapplications.glassconsole.client

/**
 * Exceptions thrown by the GlassClient SDK.
 */
class GlassClientException(message: String): Exception(message)

/**
 * HTTP Communication Error when communicating with the display server.
 */
class HttpException(
    val statusCode: Int,
    val body: String,
): Exception("HTTP Error communicating with GlassClient: $statusCode")
