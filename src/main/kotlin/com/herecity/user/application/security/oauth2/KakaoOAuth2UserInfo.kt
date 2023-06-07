package com.herecity.user.application.security.oauth2

class KakaoOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {
    override fun getId(): String = attributes["id"].toString()
    override fun getName(): String {
        val properties = attributes["properties"] as Map<String, Object>? ?: return ""
        return properties["nickname"].toString()
    }

    override fun getEmail(): String {
        val email = attributes["account_email"] ?: throw NoSuchFieldException("카카오 계정의 이메일 정보를 가져올 수 없습니다.")
        return email.toString()
    }

    override fun getImageUrl(): String {
        val properties = attributes["properties"] as Map<String, Any>? ?: return ""
        return properties["thumbnail_image"].toString()
    }
}
