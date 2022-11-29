package com.herecity.member.application.port.input

import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.application.dto.AddMemberDto
import com.herecity.member.application.dto.UpdateMemberDto

interface RecordMemberUseCase {
  fun addMember(addMemberDto: AddMemberDto): MemberDto
  fun updateMember(id: Long, updateMemberDto: UpdateMemberDto): MemberDto
  fun deleteMember(id: Long)
}
