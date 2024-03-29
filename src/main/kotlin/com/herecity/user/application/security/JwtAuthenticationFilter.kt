package com.herecity.user.application.security

import io.jsonwebtoken.io.IOException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtService: JwtService,
) : OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        response.apply {
            setHeader("Access-Control-Allow-Origin", "*")
            setHeader("Access-Control-Allow-Methods", "*")
            setHeader("Access-Control-Max-Age", "600")
            setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization")
        }

        if (request.requestURI.contains("/api/v1/auth")) {
            chain.doFilter(request, response)
            return
        }

        request.getHeader(JwtService.AUTHORIZATION_HEADER_KEY).run {
            runCatching {
                if (this == null) return@runCatching
                val token = jwtService.resolveToken(this)
                jwtService.validateToken(token)
                SecurityContextHolder.getContext().authentication = jwtService.getAuthentication(token)
            }.onFailure {
                log.error(it.message, it)
                response.sendError(HttpStatus.UNAUTHORIZED.value(), it.message)
                response.flushBuffer()
            }
        }

        chain.doFilter(request, response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
