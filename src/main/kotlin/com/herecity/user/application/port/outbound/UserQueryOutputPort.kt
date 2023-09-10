package com.herecity.user.application.port.outbound

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.user.domain.entity.User
import java.util.UUID

interface UserQueryOutputPort : BaseQueryRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findByProviderId(providerId: String): User?
    fun findAllById(ids: List<UUID>): List<User>
}
