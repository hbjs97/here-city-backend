package com.herecity.place.adapter.inbound.web.response.type

import com.herecity.place.application.port.input.type.UpdatePlaceTypeCommand

data class UpdatePlaceTypeResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(payload: UpdatePlaceTypeCommand.Out): UpdatePlaceTypeResponse =
            UpdatePlaceTypeResponse(
                id = payload.id,
                name = payload.name,
            )
    }
}
