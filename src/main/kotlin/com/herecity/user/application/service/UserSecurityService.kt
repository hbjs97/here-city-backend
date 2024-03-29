package com.herecity.user.application.service

import com.herecity.user.application.port.outbound.UserQueryOutputPort
import com.herecity.user.domain.exception.UserNotFoundException
import com.herecity.user.domain.vo.UserDetail
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserSecurityService(
    private val userQueryOutputPort: UserQueryOutputPort,
) : UserDetailsService {
    override fun loadUserByUsername(providerId: String): UserDetails {
        val user = userQueryOutputPort.findByProviderId(providerId) ?: throw UserNotFoundException()
        return UserDetail(user)
    }
}
