package com.herecity.unit.application.port.input

import com.herecity.unit.adapter.dto.UnitMemberDto
import com.herecity.unit.application.dto.UnitDto

interface LoadUnitUseCase {
  fun getAllUnits(): List<UnitDto>
  fun getUnitMembers(unitId: Long): List<UnitMemberDto>
}
