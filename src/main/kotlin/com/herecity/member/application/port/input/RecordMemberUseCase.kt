package com.herecity.member.application.port.input

import com.herecity.member.adapter.dto.MemberDto

interface RecordMemberUseCase {
  fun createMember(name: String): MemberDto
  fun updateMember(id: Long, name: String): MemberDto
  fun deleteMember(id: Long)
}
