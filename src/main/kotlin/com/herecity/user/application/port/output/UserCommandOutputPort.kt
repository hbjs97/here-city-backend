package com.herecity.user.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.user.domain.entity.User
import java.util.*

interface UserCommandOutputPort : BaseCommandRepository<User, UUID>
