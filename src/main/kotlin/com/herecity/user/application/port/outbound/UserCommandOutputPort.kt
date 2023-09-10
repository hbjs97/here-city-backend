package com.herecity.user.application.port.outbound

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.user.domain.entity.User
import java.util.UUID

interface UserCommandOutputPort : BaseCommandRepository<User, UUID>
