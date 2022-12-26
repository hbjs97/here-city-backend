package com.herecity.unit.application.port.input

import com.herecity.unit.application.dto.UnitDto
import com.herecity.unit.application.dto.UpdateUnitDto

interface RecordUnitUseCase {
  fun createUnit(name: String): UnitDto
  fun updateUnit(id: Long, updateUnitDto: UpdateUnitDto): UnitDto
  fun deleteUnit(id: Long)
}
