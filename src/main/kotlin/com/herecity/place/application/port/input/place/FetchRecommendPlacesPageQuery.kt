package com.herecity.place.application.port.input.place

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto
import java.util.UUID

interface FetchRecommendPlacesPageQuery {
    fun fetchRecommendPlacesPage(query: In): Out
    data class In(
        val userId: UUID,
        val offsetPageable: OffSetPageable,
        val placeTypeId: Long?,
        val name: String?,
        val regionIds: List<Long> = emptyList(),
        val activityId: List<Long> = emptyList(),
        val unitIds: List<Long> = emptyList(),
    )

    data class Out(
        val places: List<PlaceDto>,
        val meta: OffsetPageMeta,
    )
}
