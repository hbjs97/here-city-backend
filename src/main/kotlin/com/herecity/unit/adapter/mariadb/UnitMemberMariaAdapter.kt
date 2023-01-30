package com.herecity.unit.adapter.mariadb

import com.herecity.unit.adapter.dto.UnitMemberDto
import com.herecity.unit.application.port.output.UnitMemberCommandOutputPort
import com.herecity.unit.application.port.output.UnitMemberQueryOutputPort
import com.herecity.unit.domain.entity.QUnitMember.unitMember
import com.herecity.unit.domain.entity.UnitMember
import com.herecity.unit.domain.vo.UnitMemberId
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UnitMemberMariaAdapter(
  private val unitMemberRepository: UnitMemberRepository,
  private val queryFactory: JPAQueryFactory
) : UnitMemberQueryOutputPort, UnitMemberCommandOutputPort {

  override fun search(unitId: Long): List<UnitMemberDto> = this.queryFactory
    .select(
      Projections.constructor(
        UnitMemberDto::class.java,
        unitMember.unit.id.`as`("unitId"),
        unitMember.unit.name.`as`("unitName"),
        unitMember.member.id.`as`("memberId"),
        unitMember.member.name.`as`("memberName"),
      )
    )
    .from(unitMember)
    .innerJoin(unitMember.member)
    .innerJoin(unitMember.unit)
    .fetch()

  override fun deleteById(id: UnitMemberId) = this.unitMemberRepository.deleteById(id)

  override fun save(entity: UnitMember): UnitMember {
    return this.unitMemberRepository.save(entity)
  }


  override fun getById(id: UnitMemberId): UnitMember = this.unitMemberRepository.findById(id).orElseThrow()

  override fun findById(id: UnitMemberId): UnitMember? = this.unitMemberRepository.findById(id).get()

}
