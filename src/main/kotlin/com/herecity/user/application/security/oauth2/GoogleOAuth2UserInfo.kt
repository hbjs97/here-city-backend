package com.herecity.user.application.security.oauth2

class GoogleOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {
    override fun getId(): String = attributes["sub"].toString()
    override fun getName(): String = attributes["name"].toString()
    override fun getEmail(): String? = attributes["email"].toString()
    override fun getImageUrl(): String = attributes["picture"].toString()
}
