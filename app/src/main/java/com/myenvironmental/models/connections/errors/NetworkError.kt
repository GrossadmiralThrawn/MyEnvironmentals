package com.myenvironmental.models.connections.errors




enum class NetworkError: Error {
    UNAUTHORIZED,
    CONFLICT,
    REQUEST_TIMEOUT,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    NO_INTERNET,
    SERIALIZATION,
    UNKNOWN
}
