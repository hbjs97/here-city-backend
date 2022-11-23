package com.herecity.member.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class DuplicateMemberNameException(message: String? = "중복된 멤버 이름입니다.") : IllegalArgumentException(message)
