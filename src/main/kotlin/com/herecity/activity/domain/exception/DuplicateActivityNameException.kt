package com.herecity.activity.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class DuplicateActivityNameException(message: String? = "중복된 활동 이름입니다.") : IllegalArgumentException(message)
