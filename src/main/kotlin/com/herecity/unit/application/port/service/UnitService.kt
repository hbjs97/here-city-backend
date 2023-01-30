package com.herecity.unit.application.port.service

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.member.application.dto.AddMemberDto
import com.herecity.member.application.port.output.MemberQueryOutputPort
import com.herecity.unit.adapter.dto.UnitMemberDto
import com.herecity.unit.application.dto.UnitDto
import com.herecity.unit.application.port.input.LoadUnitUseCase
import com.herecity.unit.application.port.input.RecordUnitUseCase
import com.herecity.unit.application.port.output.UnitCommandOutputPort
import com.herecity.unit.application.port.output.UnitMemberCommandOutputPort
import com.herecity.unit.application.port.output.UnitMemberQueryOutputPort
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import com.herecity.unit.domain.entity.Unit
import com.herecity.unit.domain.entity.UnitMember
import org.springframework.stereotype.Service

@Service
class UnitService(
  private val unitQueryOutputPort: UnitQueryOutputPort,
  private val unitCommandOutputPort: UnitCommandOutputPort,
  private val unitMemberQueryOutputPort: UnitMemberQueryOutputPort,
  private val unitMemberCommandOutputPort: UnitMemberCommandOutputPort,
  private val memberQueryOutputPort: MemberQueryOutputPort,
) : LoadUnitUseCase, RecordUnitUseCase {
  override fun getAllUnits(): List<UnitDto> {
    val units = this.unitQueryOutputPort.findAll()
    return units.map { UnitDto(it) }
  }

  override fun getUnitMembers(unitId: Long): List<UnitMemberDto> = this.unitMemberQueryOutputPort.search(unitId)

  override fun createUnit(name: String): UnitDto {
    val exist = this.unitQueryOutputPort.findByName(name) !== null
    if (exist) throw DuplicateActivityNameException()
    val unit = this.unitCommandOutputPort.save(Unit(name = name))
    return UnitDto(unit)
  }

  override fun addUnitMember(addMemberDto: AddMemberDto): UnitMemberDto {
    val unit = this.unitQueryOutputPort.getById(addMemberDto.unitId)
    val member = this.memberQueryOutputPort.getById(addMemberDto.memberId)
    unit.addMember(UnitMember(unit = unit, member = member))
    this.unitCommandOutputPort.save(unit)
    return UnitMemberDto(unit, member)
  }

  override fun updateUnit(id: Long, name: String): UnitDto {
    val unit = this.unitQueryOutputPort.getById(id)
    unit.name = name
    this.unitCommandOutputPort.save(unit)
    return UnitDto(unit)
  }

  override fun deleteUnit(id: Long) = this.unitCommandOutputPort.deleteById(id)
}
