package com.herecity.place.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel

data class GetPlacesDto(
    val placeTypeId: Long?,
    val activityId: List<Long> = emptyList(),
    val unitId: List<Long> = emptyList(),
    val name: String?,
    val x: Double?,
    val y: Double?,
) {
    @Schema(hidden = true)
    val point: Point? = if (this.x == null || this.y == null) {
        null
    } else {
        GeometryFactory(PrecisionModel(), SRID).createPoint(
            Coordinate(this.x, this.y)
        )
    }

    fun coordinates(): Coordinate = Coordinate(this.x!!, this.y!!)

    companion object {
        private const val SRID = 4326
    }
}
