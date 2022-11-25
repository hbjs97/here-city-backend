package com.herecity.unit.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.unit.domain.entity.Unit


interface UnitQueryOutputPort : BaseQueryRepository<Unit, Long> {
  fun findAll(): List<Unit>
  fun findByName(name: String): Unit?
}
