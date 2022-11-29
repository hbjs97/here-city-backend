package com.herecity.region.adapter.mariadb

import com.herecity.region.domain.entity.Street
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StreetRepository : JpaRepository<Street, Long> {
  fun findByName(name: String): Street?
  fun existsByCityIdAndName(cityId: Long, name: String): Boolean
}
