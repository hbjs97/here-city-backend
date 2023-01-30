package com.herecity.member.application.port.input

import com.herecity.member.adapter.dto.MemberDto

interface LoadMemberUseCase {
  fun getMembers(): List<MemberDto>
}
