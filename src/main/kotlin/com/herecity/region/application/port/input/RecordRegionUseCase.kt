package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.dto.UpdateRegionDto

interface RecordRegionUseCase {
    fun createRegion(name: String): RegionDto
    fun updateRegion(id: Long, updateRegionDto: UpdateRegionDto): RegionDto
    fun deleteRegion(id: Long)
}
