package com.herecity.user.framework.adapter.output.mariadb

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {
  fun findByEmail(email: String): UserEntity?
}
