package com.herecity.unit.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class DuplicateUnitNameException(message: String? = "중복된 유닛 이름입니다.") : IllegalArgumentException(message)
