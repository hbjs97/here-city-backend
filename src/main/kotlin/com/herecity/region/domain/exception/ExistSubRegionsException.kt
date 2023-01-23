package com.herecity.region.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN)
class ExistSubRegionsException(message: String? = "하위 지역이 존재합니다.") : IllegalArgumentException(message)
