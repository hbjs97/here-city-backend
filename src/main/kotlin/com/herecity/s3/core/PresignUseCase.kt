package com.herecity.s3.core

import java.net.URL

interface PresignUseCase {
    fun generatePresignedUrl(
        objectKey: String,
        method: ClientProperties.HttpMethod,
        expireSecond: Long = ClientProperties.PRE_SIGNED_URL_EXPIRE_SECOND,
    ): URL
}
