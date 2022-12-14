package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.RegionDto

interface LoadRegionUseCase {
  fun getUpperRegions(): List<RegionDto>
  fun getSubRegions(upperRegionId: Long): List<RegionDto>
}
