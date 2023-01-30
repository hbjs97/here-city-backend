package com.herecity.member.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.member.domain.entity.Member


interface MemberQueryOutputPort : BaseQueryRepository<Member, Long> {
  fun findAll(): List<Member>
  fun findByName(name: String): Member?
}
