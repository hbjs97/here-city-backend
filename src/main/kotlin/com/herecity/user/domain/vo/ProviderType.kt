package com.herecity.user.domain.vo

import com.herecity.user.application.security.oauth2.GoogleOAuth2UserInfo
import com.herecity.user.application.security.oauth2.OAuth2UserInfo

enum class ProviderType {
    GOOGLE {
        override fun toOauth2UserInfo(attributes: Map<String, Any>): OAuth2UserInfo = GoogleOAuth2UserInfo(attributes)
    },
    TWITTER {
        override fun toOauth2UserInfo(attributes: Map<String, Any>): OAuth2UserInfo {
            TODO("Not yet implemented")
        }
    },
    NAVER {
        override fun toOauth2UserInfo(attributes: Map<String, Any>): OAuth2UserInfo {
            TODO("Not yet implemented")
        }
    },
    KAKAO {
        override fun toOauth2UserInfo(attributes: Map<String, Any>): OAuth2UserInfo {
            TODO("Not yet implemented")
        }
    };

    abstract fun toOauth2UserInfo(attributes: Map<String, Any>): OAuth2UserInfo
}
