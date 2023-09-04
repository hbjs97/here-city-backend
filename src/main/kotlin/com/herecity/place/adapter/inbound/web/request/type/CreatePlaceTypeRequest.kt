package com.herecity.place.adapter.inbound.web.request.type

import com.herecity.place.application.port.input.type.CreatePlaceTypeCommand
import javax.validation.constraints.Size

data class CreatePlaceTypeRequest(
    @field:Size(min = 2, max = 50)
    val name: String,
) {
    fun toDomain(): CreatePlaceTypeCommand.In = CreatePlaceTypeCommand.In(
        name = name,
    )
}
