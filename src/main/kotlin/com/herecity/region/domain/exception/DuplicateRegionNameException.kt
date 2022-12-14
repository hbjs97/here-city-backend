package com.herecity.region.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class DuplicateRegionNameException(message: String? = "중복된 지역 이름입니다.") : IllegalArgumentException(message)
