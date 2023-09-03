package com.herecity.region.application.port.output

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.domain.entity.Region

interface RegionQueryOutputPort {
    fun getRegions(): List<RegionDto>
    fun findById(id: Long): Region?
    fun findByName(name: String): Region?
    fun existsByName(name: String): Boolean
    fun getById(id: Long): Region
}
