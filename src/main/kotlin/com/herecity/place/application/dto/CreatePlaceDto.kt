package com.herecity.place.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import javax.validation.constraints.Size
import org.springframework.data.geo.Point as Location

data class CreatePlaceDto(
    @field:Size(min = 1)
    val unitIds: List<Long>,

    @field:Size(min = 1)
    val activityIds: List<Long>,

    @field:Size(min = 1)
    val placeTypeIds: List<Long>,
    val regionId: Long,
    val title: String,
    val name: String,
    val address: String,
    val description: String,
    val location: Location,
    val images: List<String> = listOf(),

    @field:Size(max = 1)
    val visitDate: String,
) {
    @Schema(hidden = true)
    val point: Point = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(this.location.x, this.location.y))
}
