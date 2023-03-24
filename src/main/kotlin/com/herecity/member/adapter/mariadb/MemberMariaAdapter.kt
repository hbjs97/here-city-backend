package com.herecity.member.adapter.mariadb

import com.herecity.member.application.port.output.MemberCommandOutputPort
import com.herecity.member.application.port.output.MemberQueryOutputPort
import com.herecity.member.domain.entity.Member
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class MemberMariaAdapter(
  private val memberRepository: MemberRepository,
) : MemberQueryOutputPort, MemberCommandOutputPort {

  override fun getById(id: Long): Member = this.memberRepository.findById(id).orElseThrow()

  override fun findById(id: Long): Member? = this.memberRepository.findById(id).get()

  override fun save(entity: Member): Member = this.memberRepository.save(entity)
  override fun findAll(): List<Member> = this.memberRepository.findAll()

  override fun findByName(name: String): Member? = this.memberRepository.findByName(name)

  override fun deleteById(id: Long) = this.memberRepository.deleteById(id)
}
