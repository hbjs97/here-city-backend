package com.herecity.region.application.service

import com.herecity.region.application.dto.RegionDto
import com.herecity.region.application.port.inbound.FetchAllRegionsQuery
import com.herecity.region.application.port.outbound.RegionQueryOutputPort
import org.springframework.stereotype.Service

@Service
class RegionQueryService(
    private val regionQueryOutputPort: RegionQueryOutputPort,
) : FetchAllRegionsQuery {
    override fun fetchRegions(): FetchAllRegionsQuery.Out {
        val regions = regionQueryOutputPort.getRegions()
        return FetchAllRegionsQuery.Out(regions.map { RegionDto(it.id, it.name) })
    }
}
