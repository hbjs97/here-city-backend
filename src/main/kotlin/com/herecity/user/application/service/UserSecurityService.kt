package com.herecity.user.application.service

import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.UserDetail
import com.herecity.user.domain.exception.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID




@Service
class UserSecurityService(
    private val userQueryOutputPort: UserQueryOutputPort,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userQueryOutputPort.findByEmail(email) ?: throw UserNotFoundException()
        return UserDetail(user)
    }

    fun loadUserById(id: UUID): UserDetails = UserDetail(userQueryOutputPort.getById(id))
}
