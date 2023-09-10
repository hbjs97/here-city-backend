package com.herecity.unit.application.port.inbound

import com.herecity.unit.application.dto.UnitDto

interface FetchUnitsQuery {
    fun fetchUnits(): Out
    data class Out(
        val units: List<UnitDto>,
    )
}
