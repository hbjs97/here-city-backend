package com.herecity.place.adapter.inbound.web.response.type

import com.herecity.place.application.port.input.type.CreatePlaceTypeCommand

data class CreatePlaceTypeResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(payload: CreatePlaceTypeCommand.Out): CreatePlaceTypeResponse =
            CreatePlaceTypeResponse(
                id = payload.id,
                name = payload.name,
            )
    }
}
