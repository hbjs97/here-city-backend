package com.herecity.unit.adapter.inbound.web.request

import com.herecity.unit.application.port.inbound.UpdateUnitCommand
import javax.validation.constraints.Size

data class UpdateUnitRequest(
    @field:Size(max = 20)
    val name: String,
) {
    fun toDomain(id: Long): UpdateUnitCommand.In = UpdateUnitCommand.In(
        id = id,
        name = name,
    )
}
