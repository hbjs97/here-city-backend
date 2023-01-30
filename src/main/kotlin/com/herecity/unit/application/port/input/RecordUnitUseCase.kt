package com.herecity.unit.application.port.input

import com.herecity.member.application.dto.AddMemberDto
import com.herecity.unit.adapter.dto.UnitMemberDto
import com.herecity.unit.application.dto.UnitDto

interface RecordUnitUseCase {
  fun createUnit(name: String): UnitDto
  fun addUnitMember(addMemberDto: AddMemberDto): UnitMemberDto
  fun updateUnit(id: Long, name: String): UnitDto
  fun deleteUnit(id: Long)
}
