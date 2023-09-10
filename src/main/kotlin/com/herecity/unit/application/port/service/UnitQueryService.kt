package com.herecity.unit.application.port.service

import com.herecity.unit.application.dto.UnitDto
import com.herecity.unit.application.port.inbound.FetchUnitsQuery
import com.herecity.unit.application.port.outbound.UnitQueryOutputPort
import org.springframework.stereotype.Service

@Service
class UnitQueryService(
    private val unitQueryOutputPort: UnitQueryOutputPort,
) : FetchUnitsQuery {
    override fun fetchUnits(): FetchUnitsQuery.Out {
        val units = unitQueryOutputPort.findAll()
        return FetchUnitsQuery.Out(
            units = units.map { UnitDto(id = it.id, name = it.name) }
        )
    }
}
