package com.herecity.user.application.service

import com.herecity.user.application.port.output.FetchUserPort
import com.herecity.user.domain.UserSecurity
import com.herecity.user.domain.exception.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserSecurityService(private val fetchUserPort: FetchUserPort) : UserDetailsService {
  override fun loadUserByUsername(email: String): UserDetails {
    val user = fetchUserPort.findByEmail(email) ?: throw UserNotFoundException()
    return UserSecurity(user)
  }
}
