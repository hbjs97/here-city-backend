package com.herecity.place.application.port.input.like

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto
import java.util.UUID

interface FetchMyPlacesQuery {
    fun fetchMyPlaces(query: In): Out
    data class In(
        val userId: UUID,
        val offSetPageable: OffSetPageable,
        val x: Double? = null,
        val y: Double? = null,
    )

    data class Out(
        val places: List<PlaceDto>,
        val meta: OffsetPageMeta,
    )
}
