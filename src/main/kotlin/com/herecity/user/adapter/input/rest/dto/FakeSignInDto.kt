package com.herecity.user.adapter.input.rest.dto

import com.herecity.user.domain.vo.UserRole

data class FakeSignInDto(val role: UserRole = UserRole.USER)
