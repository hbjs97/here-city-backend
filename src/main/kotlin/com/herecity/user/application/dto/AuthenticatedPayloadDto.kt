package com.herecity.user.application.dto

data class AuthenticatedPayloadDto(
    val token: JwtToken,
    val user: UserDto
)
