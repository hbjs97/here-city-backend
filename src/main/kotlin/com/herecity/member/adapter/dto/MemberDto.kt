package com.herecity.member.adapter.dto

import com.herecity.member.domain.entity.Member
import com.herecity.unit.application.dto.UnitDto
import com.herecity.unit.domain.entity.Unit

data class MemberDto(
  val id: Long?,
  val name: String,
  val unit: UnitDto
) {

  constructor(member: Member, unit: Unit) : this(id = member.id, name = member.name, unit = UnitDto(unit))

  constructor(id: Long, name: String, unitId: Long, unitName: String) : this(id = id, name = name, unit = UnitDto(unitId, unitName))
}
