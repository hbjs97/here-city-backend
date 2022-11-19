package com.herecity.user.application.service

import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.UserDetail
import com.herecity.user.domain.exception.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserSecurityService(private val userQueryOutputPort: UserQueryOutputPort, private val passwordEncoder: PasswordEncoder) : UserDetailsService {
  override fun loadUserByUsername(email: String): UserDetails {
    val user = userQueryOutputPort.findByEmail(email) ?: throw UserNotFoundException()
    return UserDetail(user, passwordEncoder)
  }
}
