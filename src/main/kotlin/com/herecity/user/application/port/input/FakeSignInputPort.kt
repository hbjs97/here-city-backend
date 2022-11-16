package com.herecity.user.application.port.input

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.output.UserCommandOutputPort
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.application.usecase.FakeSignUseCase
import com.herecity.user.domain.UserRole
import com.herecity.user.domain.entity.User
import com.herecity.user.framework.adapter.output.mariadb.UserEntity
import io.github.serpro69.kfaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class FakeSignInputPort @Autowired constructor(
  val userQuery: UserQueryOutputPort,
  val userCommand: UserCommandOutputPort,
  val passwordEncoder: PasswordEncoder
) : FakeSignUseCase {
  override fun fakeSignIn(role: UserRole): UserDto {
    val user = userCommand.save(
      User(
        UserEntity(
          id = UUID.randomUUID(),
          email = Faker().internet.unique.email(),
          displayName = Faker().name.firstName(),
          password = passwordEncoder.encode(Faker().random.randomString()),
          role = role
        )
      )
    )
    return UserDto(user)
  }
}
