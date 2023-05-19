package com.herecity.tour.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN)
class IsNotTourHostException(message: String? = "투어의 호스트가 아닙니다.") : RuntimeException(message)