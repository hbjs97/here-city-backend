package com.herecity.unit.application.port.service

import com.herecity.member.domain.entity.Unit
import com.herecity.unit.application.dto.UnitDto
import com.herecity.unit.application.port.input.LoadUnitUseCase
import com.herecity.unit.application.port.input.RecordUnitUseCase
import com.herecity.unit.application.port.output.UnitCommandOutputPort
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import com.herecity.unit.domain.exception.DuplicateUnitNameException
import org.springframework.stereotype.Service

@Service
class UnitService(private val unitQueryOutputPort: UnitQueryOutputPort, private val unitCommandOutputPort: UnitCommandOutputPort) : LoadUnitUseCase, RecordUnitUseCase {
  override fun getAllUnits(): List<UnitDto> {
    val units = this.unitQueryOutputPort.findAll()
    return units.map { UnitDto(it) }
  }

  override fun createUnit(name: String): UnitDto {
    val exist = this.unitQueryOutputPort.findByName(name) !== null
    if (exist) throw DuplicateUnitNameException()
    val unit = this.unitCommandOutputPort.save(Unit(name = name))
    return UnitDto(unit)
  }

  override fun updateUnit(id: Long, name: String): UnitDto {
    val unit = this.unitQueryOutputPort.getById(id)
    unit.name = name
    this.unitCommandOutputPort.save(unit)
    return UnitDto(unit)
  }

  override fun deleteUnit(id: Long) = this.unitCommandOutputPort.deleteById(id)
}
