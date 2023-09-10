package com.herecity.region.application.port.outbound

import com.herecity.region.domain.entity.Region

interface RegionCommandOutputPort {
    fun save(entity: Region): Region
}
