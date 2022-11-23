package com.herecity.member.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.member.domain.entity.Member

interface MemberCommandOutputPort : BaseCommandRepository<Member, Long> {
  fun deleteById(id: Long)
}
