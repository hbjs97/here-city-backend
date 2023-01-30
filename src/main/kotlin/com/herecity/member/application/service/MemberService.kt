package com.herecity.member.application.service

import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.application.port.input.LoadMemberUseCase
import com.herecity.member.application.port.input.RecordMemberUseCase
import com.herecity.member.application.port.output.MemberCommandOutputPort
import com.herecity.member.application.port.output.MemberQueryOutputPort
import com.herecity.member.domain.entity.Member
import com.herecity.member.domain.exception.DuplicateMemberNameException
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberQueryOutputPort: MemberQueryOutputPort, private val memberCommandOutputPort: MemberCommandOutputPort) :
  LoadMemberUseCase, RecordMemberUseCase {

  override fun getMembers(): List<MemberDto> {
    val members = this.memberQueryOutputPort.findAll()
    return members.map { MemberDto(it.id, it.name) }
  }

  override fun createMember(name: String): MemberDto {
    val duplicatedName = this.memberQueryOutputPort.findByName(name) != null
    if (duplicatedName) throw DuplicateMemberNameException()
    val member = this.memberCommandOutputPort.save(Member(name = name))
    return MemberDto(member.id, member.name)
  }


  override fun updateMember(id: Long, name: String): MemberDto {
    val member = this.memberQueryOutputPort.getById(id)
    member.name = name
    this.memberCommandOutputPort.save(member)
    return MemberDto(member.id, member.name)
  }

  override fun deleteMember(id: Long) = this.memberCommandOutputPort.deleteById(id)

}
