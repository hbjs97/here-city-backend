package com.herecity.unit.adapter.mariadb

import com.herecity.unit.domain.entity.UnitMember
import com.herecity.unit.domain.vo.UnitMemberId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UnitMemberRepository : JpaRepository<UnitMember, UnitMemberId>
