package com.herecity.s3.core

interface MethodMapper<T> {
    fun map(method: ClientProperties.HttpMethod): T
}
