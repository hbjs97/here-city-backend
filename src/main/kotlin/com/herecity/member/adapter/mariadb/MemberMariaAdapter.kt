package com.herecity.member.adapter.mariadb

import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.adapter.dto.SearchMemberDto
import com.herecity.member.application.port.output.MemberCommandOutputPort
import com.herecity.member.application.port.output.MemberQueryOutputPort
import com.herecity.member.domain.entity.Member
import com.herecity.member.domain.entity.QMember.member
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class MemberMariaAdapter(
  private val memberRepository: MemberRepository,
  private val queryFactory: JPAQueryFactory
) : MemberQueryOutputPort, MemberCommandOutputPort {

  override fun search(searchMemberDto: SearchMemberDto): List<MemberDto> = this.queryFactory
    .select(
      Projections.constructor(
        MemberDto::class.java,
        member.id,
        member.name,
        member.unit.id.`as`("unitId"),
        member.unit.name.`as`("unitName")
      )
    )
    .from(member)
    .innerJoin(member.unit)
    .where(this.eqUnitId(searchMemberDto.unitId))
    .fetch()

  override fun getById(id: Long): Member = this.memberRepository.findById(id).orElseThrow()

  override fun findById(id: Long): Member? = this.memberRepository.findById(id).get()

  override fun save(unit: Member): Member = this.memberRepository.save(unit)

  override fun findByName(name: String): Member? = this.memberRepository.findByName(name)

  override fun deleteById(id: Long) = this.memberRepository.deleteById(id)

  private fun eqUnitId(unitId: Long?): BooleanExpression? {
    if (unitId == null) return null
    return member.unitId.eq(unitId)
  }
}
