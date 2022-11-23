package com.herecity.member.application.port.input

import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.adapter.dto.SearchMemberDto

interface LoadMemberUseCase {
  fun getMembers(searchMemberDto: SearchMemberDto): List<MemberDto>
}
