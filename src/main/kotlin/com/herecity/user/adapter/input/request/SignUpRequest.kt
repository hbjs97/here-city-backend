package com.herecity.user.adapter.input.request

import com.herecity.user.application.port.input.SignUpCommand

data class SignUpRequest(
    val email: String,
    val password: String,
    val displayName: String,
    val thumbnail: String?,
) {
    fun toDomain(): SignUpCommand.In = SignUpCommand.In(
        email = email,
        password = password,
        displayName = displayName,
        thumbnail = thumbnail,
    )
}
