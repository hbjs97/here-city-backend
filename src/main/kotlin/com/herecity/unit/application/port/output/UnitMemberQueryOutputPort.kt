package com.herecity.unit.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.unit.adapter.dto.UnitMemberDto
import com.herecity.unit.domain.entity.UnitMember
import com.herecity.unit.domain.vo.UnitMemberId


interface UnitMemberQueryOutputPort : BaseQueryRepository<UnitMember, UnitMemberId> {
  fun search(unitId: Long): List<UnitMemberDto>
}
