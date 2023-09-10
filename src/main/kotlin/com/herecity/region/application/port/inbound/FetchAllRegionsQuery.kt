package com.herecity.region.application.port.inbound

import com.herecity.region.application.dto.RegionDto

interface FetchAllRegionsQuery {
    fun fetchRegions(): Out
    data class Out(
        val regions: List<RegionDto>,
    )
}
