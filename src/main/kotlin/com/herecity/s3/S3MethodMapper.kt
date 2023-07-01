package com.herecity.s3

import com.amazonaws.HttpMethod
import com.herecity.s3.core.ClientProperties
import com.herecity.s3.core.MethodMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class S3MethodMapper : MethodMapper<HttpMethod> {
    override fun map(method: ClientProperties.HttpMethod): HttpMethod {
        return when (method) {
            ClientProperties.HttpMethod.GET -> HttpMethod.GET
            ClientProperties.HttpMethod.PUT -> HttpMethod.PUT
            ClientProperties.HttpMethod.POST -> HttpMethod.POST
            ClientProperties.HttpMethod.PATCH -> HttpMethod.PATCH
            ClientProperties.HttpMethod.DELETE -> HttpMethod.DELETE
            ClientProperties.HttpMethod.HEAD -> HttpMethod.HEAD
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
