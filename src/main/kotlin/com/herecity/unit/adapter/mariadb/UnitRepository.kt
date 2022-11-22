package com.herecity.unit.adapter.mariadb

import com.herecity.member.domain.entity.Unit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UnitRepository : JpaRepository<Unit, Long> {
  fun findByName(name: String): Unit?
}
