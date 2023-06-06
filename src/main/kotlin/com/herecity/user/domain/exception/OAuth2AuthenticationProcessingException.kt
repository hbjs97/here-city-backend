package com.herecity.user.domain.exception

import org.springframework.security.core.AuthenticationException

class OAuth2AuthenticationProcessingException(
    override val message: String,
    cause: Throwable? = null,
) : AuthenticationException(message, cause)
