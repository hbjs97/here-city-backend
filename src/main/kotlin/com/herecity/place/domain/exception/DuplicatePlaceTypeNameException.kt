package com.herecity.place.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class DuplicatePlaceTypeNameException(message: String? = "중복된 장소분류 이름입니다.") : IllegalArgumentException(message)
