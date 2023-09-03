package com.herecity.unit.application.port.input

import com.herecity.unit.application.dto.UnitDto

interface RecordUnitUseCase {
    fun createUnit(name: String): UnitDto
    fun updateUnit(id: Long, name: String): UnitDto
    fun deleteUnit(id: Long)
}
