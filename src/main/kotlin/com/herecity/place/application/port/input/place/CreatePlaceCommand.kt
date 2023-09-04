package com.herecity.place.application.port.input.place

import com.herecity.place.application.dto.Coordinate2D
import org.locationtech.jts.geom.Point
import org.springframework.data.geo.Point as Location

interface CreatePlaceCommand {
    fun createPlace(command: In): Out
    data class In(
        val unitIds: List<Long>,
        val activityIds: List<Long>,
        val placeTypeIds: List<Long>,
        val regionId: Long,
        val title: String,
        val name: String,
        val address: String,
        val description: String,
        val location: Location,
        val point: Point,
        val images: List<String> = listOf(),
        val visitDate: String,
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
        val liked: Boolean = false,
        val distance: Double? = null,
    )
}
