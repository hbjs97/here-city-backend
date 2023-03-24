package com.herecity.region.application.port.output

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.domain.entity.Region

interface RegionQueryOutputPort {
  fun getUpperRegions(): List<RegionDto>
  fun getSubRegions(id: Long): List<RegionDto>
  fun findById(id: Long): Region?
  fun findByName(name: String): Region?
  fun existsByName(name: String): Boolean
  fun existsByUpperRegionIdAndName(upperRegionId: Long, name: String): Boolean
  fun getById(id: Long): Region
  fun hasSubRegion(id: Long): Boolean
}
