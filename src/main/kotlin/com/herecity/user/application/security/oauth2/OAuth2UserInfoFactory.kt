package com.herecity.user.application.security.oauth2

import com.herecity.user.domain.exception.OAuth2AuthenticationProcessingException
import com.herecity.user.domain.vo.ProviderType


internal object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
        runCatching {
            return ProviderType.valueOf(registrationId.uppercase()).toOauth2UserInfo(attributes)
        }.getOrElse {
            throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
        }
    }
}
