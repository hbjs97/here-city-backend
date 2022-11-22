package com.herecity.unit.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.member.domain.entity.Unit

interface UnitCommandOutputPort : BaseCommandRepository<Unit, Long> {
  fun deleteById(id: Long)
}
