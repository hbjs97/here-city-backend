package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.dto.UpdateRegionDto

interface RecordRegionUseCase {
    fun createUpperRegion(name: String): RegionDto
    fun addSubRegion(upperRegionId: Long, name: String): RegionDto
    fun updateRegion(id: Long, updateRegionDto: UpdateRegionDto): RegionDto
    fun deleteRegion(id: Long)
}
