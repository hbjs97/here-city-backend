package com.herecity.region.application.port.outbound

import com.herecity.region.domain.entity.Region

interface RegionQueryOutputPort {
    fun getRegions(): List<Region>
    fun findById(id: Long): Region?
    fun findByName(name: String): Region?
    fun existsByName(name: String): Boolean
    fun getById(id: Long): Region
}
