package com.herecity.common.interceptor

import com.herecity.common.converter.PrettyConverter
import com.herecity.common.filter.MultiAccessRequestWrapper
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.DispatcherType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpLoggingInterceptor(private val converter: PrettyConverter) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (DispatcherType.REQUEST.name != request.dispatcherType.name) return true
        val startTime = System.currentTimeMillis()
        request.setAttribute("startTime", startTime)

        val requestURI = request.requestURI
        val requestQuery = request.queryString
        val requestMethod = request.method
        val requestHeaders = request.headerNames.toList().map { it to request.getHeader(it) }
        val requestBody = when {
            request.contentType != null && !request.contentType.startsWith("multipart/form-data")
            -> converter.convert((request as MultiAccessRequestWrapper).getContents())

            else -> ""
        }

        log.info("[Request][Client] IP: ${request.remoteAddr}")
        log.info("[Request][Client] User-Agent: ${request.getHeader("User-Agent")}")
        log.info("[Request][Headers] $requestHeaders")
        log.info("[Request][$requestMethod] $requestURI")
        log.info("[Request][Query] $requestQuery")
        log.info("[Request][Body] $requestBody")

        return super.preHandle(request, response, handler)
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?,
    ) {
        val startTime = request.getAttribute("startTime") as Long
        val endTime = System.currentTimeMillis()
        val processingTime = endTime - startTime

        val requestUrl = request.requestURL.toString()
        val method = request.method
        val status = response.status
        log.info("[Response] URL: $requestUrl, Method: $method, Status: $status, Processing Time: $processingTime ms")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
