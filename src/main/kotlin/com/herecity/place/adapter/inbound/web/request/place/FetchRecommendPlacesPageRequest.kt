package com.herecity.place.adapter.inbound.web.request.place

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.place.FetchRecommendPlacesPageQuery
import java.util.UUID

data class FetchRecommendPlacesPageRequest(
    val regionIds: List<Long>?,
    val activityId: List<Long>?,
    val unitIds: List<Long>?,
    val placeTypeId: Long?,
    val name: String?,
) {
    fun toDomain(userId: UUID, offSetPageable: OffSetPageable):
        FetchRecommendPlacesPageQuery.In = FetchRecommendPlacesPageQuery.In(
        userId = userId,
        offsetPageable = offSetPageable,
        regionIds = regionIds ?: emptyList(),
        placeTypeId = placeTypeId,
        activityId = activityId ?: emptyList(),
        unitIds = unitIds ?: emptyList(),
        name = name,
    )
}
