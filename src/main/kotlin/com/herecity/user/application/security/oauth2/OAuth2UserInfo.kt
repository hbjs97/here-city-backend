package com.herecity.user.application.security.oauth2

abstract class OAuth2UserInfo(private var _attributes: Map<String, Any> = mapOf()) {
    val attributes: Map<String, Any>
        get() = _attributes

    abstract fun getId(): String

    abstract fun getName(): String

    abstract fun getEmail(): String?

    abstract fun getImageUrl(): String
}
