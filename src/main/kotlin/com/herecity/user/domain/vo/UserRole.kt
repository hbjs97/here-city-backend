package com.herecity.user.domain.vo

enum class UserRole(private val key: String) {
    ANONYMOUS("ROLE_ANONYMOUS"), USER("ROLE_USER"), ADMIN("ROLE_ADMIN")
}
