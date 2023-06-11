package com.herecity.common.interceptor

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.util.Optional
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class MDCLoggingInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        MDC.put(REQUEST_IP_MDC_KEY, request.remoteAddr)
        MDC.put(SPAN_ID, request.getHeader(SPAN_ID))
        MDC.put(TRACE_ID, request.getHeader(TRACE_ID))
        if (handler is HandlerMethod) {
            val fullUrl =
                request.requestURI + Optional.ofNullable(request.queryString).map { qs: String -> "?$qs" }.orElse("")
            val handlerMethod = handler
            val controllerInfo = handlerMethod.beanType.simpleName + "#" + handlerMethod.method.name
            log.debug("Request URL: {} Controller: {}", fullUrl, controllerInfo)
            MDC.put(REQUEST_URL_MDC_KEY, fullUrl)
            MDC.put(REQUEST_CONTROLLER_MDC_KEY, controllerInfo)
        }
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        MDC.clear()
    }

    companion object {
        const val TRACE_ID = "x-b3-traceid"
        const val SPAN_ID = "x-b3-spanid"
        const val REQUEST_IP_MDC_KEY = "REQUEST_IP"
        const val REQUEST_URL_MDC_KEY = "URL"
        const val REQUEST_CONTROLLER_MDC_KEY = "Controller"
        private val log = LoggerFactory.getLogger(MDCLoggingInterceptor::class.java)
    }
}
