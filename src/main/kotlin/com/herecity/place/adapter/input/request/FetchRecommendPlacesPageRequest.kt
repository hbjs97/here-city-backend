package com.herecity.place.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.FetchRecommendPlacesPageQuery
import java.util.UUID

data class FetchRecommendPlacesPageRequest(
    val regionId: Long?,
    val placeTypeId: Long?,
    val activityId: List<Long> = emptyList(),
    val unitId: List<Long> = emptyList(),
    val name: String?,
) {
    fun toDomain(userId: UUID, offSetPageable: OffSetPageable):
        FetchRecommendPlacesPageQuery.In = FetchRecommendPlacesPageQuery.In(
        userId = userId,
        offsetPageable = offSetPageable,
        regionId = this.regionId,
        placeTypeId = this.placeTypeId,
        activityId = this.activityId,
        unitId = this.unitId,
        name = this.name,
    )
}
