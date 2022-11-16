package com.herecity.user.framework.adapter.output.mariadb

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.user.domain.UserRole
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity(name = "user")
class UserEntity(
  @Id
  @GeneratedValue(generator = "hibernate-uuid")
  @GenericGenerator(name = "uuid4", strategy = "uuid4")
  @Type(type = "uuid-char")
  var id: UUID? = null,

  @Column(length = 100, nullable = false, unique = true)
  var email: String,

  @Column(length = 20, nullable = false, unique = true)
  var displayName: String,

  @Column(length = 255, nullable = false)
  var password: String,

  @Enumerated(EnumType.STRING)
  var role: UserRole = UserRole.USER
) : BaseEntity()
