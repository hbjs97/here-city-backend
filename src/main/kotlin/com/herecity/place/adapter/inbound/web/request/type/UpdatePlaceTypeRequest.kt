package com.herecity.place.adapter.inbound.web.request.type

import com.herecity.place.application.port.input.type.UpdatePlaceTypeCommand
import javax.validation.constraints.Size

data class UpdatePlaceTypeRequest(
    @field:Size(min = 2, max = 50)
    val name: String,
) {
    fun toDomain(id: Long): UpdatePlaceTypeCommand.In = UpdatePlaceTypeCommand.In(
        id = id,
        name = name,
    )
}
