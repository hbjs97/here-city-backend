package com.herecity.s3.core

object ClientProperties {
    const val PRE_SIGNED_URL_EXPIRE_SECOND = 10L

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, PATCH;
    }
}
