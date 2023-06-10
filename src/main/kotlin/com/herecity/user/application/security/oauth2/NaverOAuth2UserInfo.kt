package com.herecity.user.application.security.oauth2

class NaverOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {
    private val _response
        get() = attributes["response"] as Map<String, Any>? ?: throw NoSuchFieldException(
            "네이버 계정정보를 가져올 수 없습니다."
        )

    override fun getId(): String = _response["id"].toString()

    override fun getName(): String = _response["nickname"].toString()

    override fun getEmail(): String? {
        val email = _response["email"] ?: return null
        return email.toString()
    }

    override fun getImageUrl(): String {
        val profileImage = _response["profile_image"] ?: ""
        return profileImage.toString()
    }
}
