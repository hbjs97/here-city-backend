package com.herecity.unit.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.unit.domain.entity.UnitMember
import com.herecity.unit.domain.vo.UnitMemberId

interface UnitMemberCommandOutputPort : BaseCommandRepository<UnitMember, UnitMemberId> {
  fun deleteById(id: UnitMemberId)
}
