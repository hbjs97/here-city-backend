package com.herecity.unit.application.port.outbound

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.unit.domain.entity.Unit

interface UnitCommandOutputPort : BaseCommandRepository<Unit, Long> {
    fun deleteById(id: Long)
}
