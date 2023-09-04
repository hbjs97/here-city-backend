package com.herecity.place.adapter.inbound.web.response.place

import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.port.input.place.UpdatePlaceCommand

data class UpdatePlaceResponse(
    val id: Long,
    val title: String,
    val name: String,
    val address: String,
    val point: Coordinate2D,
    val rating: Double,
    val images: List<String>,
    val liked: Boolean,
    val distance: Double?,
    val description: String?,
) {
    companion object {
        fun from(payload: UpdatePlaceCommand.Out): UpdatePlaceResponse =
            UpdatePlaceResponse(
                id = payload.id,
                title = payload.title,
                name = payload.name,
                address = payload.address,
                point = payload.point,
                rating = payload.rating,
                images = payload.images,
                description = payload.description,
                liked = payload.liked,
                distance = payload.distance,
            )
    }
}
