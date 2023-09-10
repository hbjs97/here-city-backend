package com.herecity.region.adapter.inbound.web.request

import com.herecity.region.application.port.inbound.UpdateRegionCommand
import javax.validation.constraints.Size

data class UpdateRegionRequest(
    @field:Size(min = 2, max = 20)
    val name: String,
) {
    fun toDomain(id: Long): UpdateRegionCommand.In = UpdateRegionCommand.In(
        id = id,
        name = name,
    )
}
