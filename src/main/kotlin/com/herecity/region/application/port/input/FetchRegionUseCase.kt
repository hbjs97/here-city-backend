package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.RegionDto

interface FetchRegionUseCase {
    fun getUpperRegions(): List<RegionDto>
    fun getSubRegions(upperRegionId: Long): List<RegionDto>
    fun getById(id: Long): RegionDto
}
