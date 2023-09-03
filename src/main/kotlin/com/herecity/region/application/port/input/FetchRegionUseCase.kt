package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.RegionDto

interface FetchRegionUseCase {
    fun getRegions(): List<RegionDto>
    fun getById(id: Long): RegionDto
}
