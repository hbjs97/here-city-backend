package com.herecity.common.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class DomainContext(
    val context: String,
    val contextDetail: String = "",
    val msg: String = "",
)
