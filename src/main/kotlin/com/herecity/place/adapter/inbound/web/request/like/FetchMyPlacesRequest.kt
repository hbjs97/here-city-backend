package com.herecity.place.adapter.inbound.web.request.like

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.like.FetchMyPlacesQuery
import java.util.UUID

data class FetchMyPlacesRequest(
    val x: Double?,
    val y: Double?,
) {
    fun toDomain(userId: UUID, offSetPageable: OffSetPageable) = FetchMyPlacesQuery.In(
        userId = userId,
        offSetPageable = offSetPageable,
        x = x,
        y = y,
    )
}
