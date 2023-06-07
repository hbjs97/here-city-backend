package com.herecity.user.application.security.oauth2

import com.herecity.user.domain.exception.OAuth2AuthenticationProcessingException
import com.herecity.user.domain.vo.ProviderType
import org.slf4j.LoggerFactory


internal object OAuth2UserInfoFactory {
    private val log = LoggerFactory.getLogger(this::class.java)
    fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
        runCatching {
            return ProviderType.valueOf(registrationId.uppercase()).toOauth2UserInfo(attributes)
        }.getOrElse {
            log.error(it.message, it)
            throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
        }
    }
}
