package com.herecity.unit.adapter.inbound.web.request

import com.herecity.unit.application.port.inbound.CreateUnitCommand
import javax.validation.constraints.Size

data class CreateUnitRequest(
    @field:Size(max = 20)
    val name: String,
) {
    fun toDomain(): CreateUnitCommand.In = CreateUnitCommand.In(
        name = name,
    )
}
