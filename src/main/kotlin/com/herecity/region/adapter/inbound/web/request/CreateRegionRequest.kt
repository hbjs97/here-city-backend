package com.herecity.region.adapter.inbound.web.request

import com.herecity.region.application.port.inbound.CreateRegionCommand
import javax.validation.constraints.Size

data class CreateRegionRequest(
    @field:Size(min = 2, max = 20)
    val name: String,
) {
    fun toDomain(): CreateRegionCommand.In = CreateRegionCommand.In(
        name = name,
    )
}
