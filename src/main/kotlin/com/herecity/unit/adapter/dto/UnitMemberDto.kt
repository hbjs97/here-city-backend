package com.herecity.unit.adapter.dto

import com.herecity.member.domain.entity.Member
import com.herecity.unit.domain.entity.Unit


data class UnitMemberDto(
  val unitId: Long?,
  val unitName: String,
  val memberId: Long?,
  val memberName: String,
) {
  constructor(unit: Unit, member: Member) : this(
    unitId = unit.id,
    unitName = unit.name,
    memberId = member.id,
    memberName = member.name,
  )
}
