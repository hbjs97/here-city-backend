package com.herecity.place.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.FetchPlacesPageQuery
import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import java.util.UUID

data class FetchPlacesPageRequest(
    val regionId: Long?,
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

    fun toDomain(userId: UUID, offSetPageable: OffSetPageable): FetchPlacesPageQuery.In = FetchPlacesPageQuery.In(
        userId = userId,
        offsetPageable = offSetPageable,
        regionId = this.regionId,
        placeTypeId = this.placeTypeId,
        activityId = this.activityId,
        unitId = this.unitId,
        name = this.name,
        point = this.point,
    )

    companion object {
        private const val SRID = 4326
    }
}
