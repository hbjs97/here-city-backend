package com.herecity.user.application.service

import com.herecity.user.application.port.input.SignUseCase
import com.herecity.user.framework.adapter.output.mariadb.UserMariaAdapter
import org.springframework.stereotype.Service

@Service
class AuthService(private val userMariaAdapter: UserMariaAdapter) : SignUseCase {
  override fun signIn(email: String, password: String): Any {
    TODO("Not yet implemented")
  }

  override fun signOut(): Any {
    TODO("Not yet implemented")
  }
}
