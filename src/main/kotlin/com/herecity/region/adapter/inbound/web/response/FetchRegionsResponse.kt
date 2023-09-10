package com.herecity.region.adapter.inbound.web.response

import com.herecity.region.application.dto.RegionDto

data class FetchRegionsResponse(
    val contents: List<RegionDto>,
)
