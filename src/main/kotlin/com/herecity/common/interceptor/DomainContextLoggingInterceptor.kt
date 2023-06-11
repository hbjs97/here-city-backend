package com.herecity.common.interceptor

import com.herecity.common.annotation.DomainContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DomainContextLoggingInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val method = handler.method
            if (method.isAnnotationPresent(DomainContext::class.java)) {
                method.getAnnotation(DomainContext::class.java).let {
                    val requestUserPrincipal = request.userPrincipal?.name ?: "anonymous"
                    log.info("[${it.context}][${it.contextDetail}] ${it.msg}, LoginId: $requestUserPrincipal")
                }
            }
        }
        return true
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
