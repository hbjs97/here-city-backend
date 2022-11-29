package com.herecity.region.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class NotExistCityException(message: String? = "존재하지 않는 도시 정보입니다.") : NoSuchElementException(message)
