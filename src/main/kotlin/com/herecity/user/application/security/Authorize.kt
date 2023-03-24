package com.herecity.user.application.security

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import java.lang.annotation.Inherited

//@SecurityRequirement(name = "BearerToken")
//sealed class Authorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SecurityRequirement(name = "BearerToken")
@Inherited
annotation class Authorize
