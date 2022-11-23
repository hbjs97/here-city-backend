package com.herecity.member.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.adapter.dto.SearchMemberDto
import com.herecity.member.domain.entity.Member


interface MemberQueryOutputPort : BaseQueryRepository<Member, Long> {
  fun searchMembers(searchMemberDto: SearchMemberDto): List<MemberDto>
  fun findByName(name: String): Member?
}
