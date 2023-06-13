package com.herecity.user.adapter.input.request

import com.herecity.user.application.port.input.SignInQuery

data class SignInRequest(
    val email: String,
    val password: String,
    val displayName: String,
    val thumbnail: String?,
) {
    fun toDomain(): SignInQuery.In = SignInQuery.In(
        email = email,
        password = password,
    )
}
