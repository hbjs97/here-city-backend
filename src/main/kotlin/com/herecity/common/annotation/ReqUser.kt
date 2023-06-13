package com.herecity.common.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@AuthenticationPrincipal(
    expression = "#this == 'anonymousUser' ? T(com.herecity.user.domain.vo.UserDetail).getAnonymousUserDetail() : #this"
)
annotation class ReqUser
