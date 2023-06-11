package com.herecity.common.config

import com.herecity.common.interceptor.DomainContextLoggingInterceptor
import com.herecity.common.interceptor.HttpLoggingInterceptor
import com.herecity.common.interceptor.MDCLoggingInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig(
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val contextLoggingInterceptor: DomainContextLoggingInterceptor,
    private val mdcLoggingInterceptor: MDCLoggingInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(contextLoggingInterceptor)
            addInterceptor(mdcLoggingInterceptor)
        }
    }
}
