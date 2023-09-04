package com.herecity.place.application.port.input.place

import com.herecity.place.application.dto.Coordinate2D

interface SavePlaceRatingCommand {
    fun savePlaceRating(command: In): Out
    data class In(
        val id: Long,
        val rating: Double,
    )

    data class Out(
        val id: Long,
        val title: String,
        val name: String,
        val address: String,
        val point: Coordinate2D,
        val rating: Double,
        val description: String?,
        val images: List<String> = emptyList(),
        var liked: Boolean = false,
        var distance: Double? = null,
    )
}
