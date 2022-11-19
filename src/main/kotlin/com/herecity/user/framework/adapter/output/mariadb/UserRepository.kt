package com.herecity.user.framework.adapter.output.mariadb

import com.herecity.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
  fun findByEmail(email: String): User?
}
