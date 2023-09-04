package com.herecity.place.application.port.input.place

import com.herecity.place.application.dto.Coordinate2D
import org.locationtech.jts.geom.Point
import org.springframework.data.geo.Point as Location

interface UpdatePlaceCommand {
    fun updatePlace(command: In): Out
    data class In(
        val id: Long,
        val unitIds: List<Long>,
        val activityIds: List<Long>,
        val placeTypeIds: List<Long>,
        val regionId: Long,
        val title: String,
        val name: String,
        val address: String,
        val description: String,
        val location: Location,
        val images: List<String> = listOf(),
        val visitDate: String,
        val point: Point,
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
