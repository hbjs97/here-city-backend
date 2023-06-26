package com.herecity.place.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.FetchNearByPlacesPageQuery
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import java.util.UUID

data class FetchNearByPlacesPageRequest(
    val regionId: Long?,
    val placeTypeId: Long?,
    val activityId: List<Long> = emptyList(),
    val unitId: List<Long> = emptyList(),
    val name: String?,
    val x: Double,
    val y: Double,
) {
    fun toDomain(userId: UUID, offSetPageable: OffSetPageable):
        FetchNearByPlacesPageQuery.In = FetchNearByPlacesPageQuery.In(
        userId = userId,
        offsetPageable = offSetPageable,
        regionId = this.regionId,
        placeTypeId = this.placeTypeId,
        activityId = this.activityId,
        unitId = this.unitId,
        name = this.name,
        point = GeometryFactory(PrecisionModel(), SRID).createPoint(
            Coordinate(this.x, this.y)
        ),
    )

    companion object {
        private const val SRID = 4326
    }
}
