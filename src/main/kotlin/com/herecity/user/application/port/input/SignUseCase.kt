package com.herecity.user.application.port.input

interface SignUseCase {
  fun signIn(email: String, password: String): Any;
  fun signOut(): Any;
}
