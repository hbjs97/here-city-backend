package com.herecity.unit.adapter.inbound.web.response

import com.herecity.unit.application.dto.UnitDto

data class FetchUnitsResponse(
    val contents: List<UnitDto>,
)
