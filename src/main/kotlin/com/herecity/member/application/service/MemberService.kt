package com.herecity.member.application.service

import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.adapter.dto.SearchMemberDto
import com.herecity.member.application.dto.AddMemberDto
import com.herecity.member.application.dto.UpdateMemberDto
import com.herecity.member.application.port.input.LoadMemberUseCase
import com.herecity.member.application.port.input.RecordMemberUseCase
import com.herecity.member.application.port.output.MemberCommandOutputPort
import com.herecity.member.application.port.output.MemberQueryOutputPort
import com.herecity.member.domain.entity.Member
import com.herecity.region.domain.exception.DuplicateCityNameException
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberQueryOutputPort: MemberQueryOutputPort, private val memberCommandOutputPort: MemberCommandOutputPort, private val unitQueryOutputPort: UnitQueryOutputPort) :
  LoadMemberUseCase, RecordMemberUseCase {
  override fun getMembers(searchMemberDto: SearchMemberDto): List<MemberDto> {
    return this.memberQueryOutputPort.search(searchMemberDto)
  }

  override fun addMember(addMemberDto: AddMemberDto): MemberDto {
    val unit = this.unitQueryOutputPort.getById(addMemberDto.unitId)
    val duplicatedName = this.memberQueryOutputPort.findByName(addMemberDto.name) != null
    if (duplicatedName) throw DuplicateCityNameException()
    val member = this.memberCommandOutputPort.save(Member(unit = unit, name = addMemberDto.name))
    return MemberDto(member, unit)
  }

  override fun updateMember(id: Long, updateMemberDto: UpdateMemberDto): MemberDto {
    val member = this.memberQueryOutputPort.getById(id)
    if (updateMemberDto.unitId != null) {
      val newUnit = this.unitQueryOutputPort.getById(updateMemberDto.unitId)
      member.unit = newUnit
    }
    if (updateMemberDto.name != null) member.name = updateMemberDto.name

    this.memberCommandOutputPort.save(member)
    return MemberDto(member, member.unit!!)
  }

  override fun deleteMember(id: Long) = this.memberCommandOutputPort.deleteById(id)
}
