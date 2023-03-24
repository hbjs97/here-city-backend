package com.herecity.user.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN)
class SignInFailException : RuntimeException("로그인을 실패했습니다.")
