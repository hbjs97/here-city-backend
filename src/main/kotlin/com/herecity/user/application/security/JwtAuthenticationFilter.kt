package com.herecity.user.application.security

import io.jsonwebtoken.io.IOException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtAuthenticationFilter(private val jwtService: JwtService, private val passwordEncoder: PasswordEncoder) : GenericFilterBean() {
  @Throws(IOException::class, ServletException::class)
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
//    val res: HttpServletResponse = response as HttpServletResponse
//    res.setHeader("Access-Control-Allow-Origin", "*")
//    res.setHeader("Access-Control-Allow-Methods", "*")
//    res.setHeader("Access-Control-Max-Age", "3600")
//    res.setHeader(
//      "Access-Control-Allow-Headers",
//      "Origin, Content-Type, Accept, Authorization"
//    )

    val token: String? = jwtService.resolveToken((request as HttpServletRequest))
    if (token != null && jwtService.validateToken(token)) {
      val authentication = jwtService.getAuthentication(token, passwordEncoder)
      SecurityContextHolder.getContext().authentication = authentication
    }
    chain.doFilter(request, response)
  }

}
