package com.herecity.place.application.port.input

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto
import java.util.UUID

interface FetchRecommendPlacesPageQuery {
    fun fetchRecommendPlacesPage(query: In): Out
    data class In(
        val userId: UUID,
        val regionId: Long?,
        val placeTypeId: Long?,
        val activityId: List<Long> = emptyList(),
        val unitId: List<Long> = emptyList(),
        val name: String?,
        val offsetPageable: OffSetPageable,
    )

    data class Out(
        val places: List<PlaceDto>,
        val meta: OffsetPageMeta,
    )
}
