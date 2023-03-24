package com.herecity.unit.application.dto

import com.herecity.unit.domain.entity.Unit

data class UnitDto(
  val id: Long?,
  val name: String
) {
  constructor(unit: Unit) : this(id = unit.id, name = unit.name)
}
