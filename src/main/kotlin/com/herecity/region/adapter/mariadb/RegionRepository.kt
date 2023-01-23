package com.herecity.region.adapter.mariadb

import com.herecity.region.domain.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository : JpaRepository<Region, Long> {
  fun findByName(name: String): Region?
  fun existsByName(name: String): Boolean
  fun existsByUpperRegionId(upperRegionId: Long): Boolean
  fun existsByUpperRegionIdAndName(upperRegionId: Long, name: String): Boolean
}
